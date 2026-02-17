package org.asdfformat.asdf.node;

import org.asdfformat.asdf.node.impl.BooleanAsdfNode;
import org.asdfformat.asdf.node.impl.MappingAsdfNode;
import org.asdfformat.asdf.node.impl.NdArrayAsdfNode;
import org.asdfformat.asdf.node.impl.NumberAsdfNode;
import org.asdfformat.asdf.node.impl.SequenceAsdfNode;
import org.asdfformat.asdf.node.impl.StringAsdfNode;
import org.asdfformat.asdf.node.impl.TimestampAsdfNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VarargMethodTest {
    private AsdfNode leafNode;
    private AsdfNode sequenceNode;
    private AsdfNode rootNode;

    @BeforeEach
    void beforeEach() {
        final Map<AsdfNode, AsdfNode> leafMap = new HashMap<>();
        leafMap.put(StringAsdfNode.of("string"), StringAsdfNode.of("string key value"));
        leafMap.put(BooleanAsdfNode.of(true), StringAsdfNode.of("boolean key value"));
        leafMap.put(NumberAsdfNode.of(1), StringAsdfNode.of("number key value"));

        leafMap.put(StringAsdfNode.of("big decimal"), NumberAsdfNode.of(BigDecimal.TEN));
        leafMap.put(StringAsdfNode.of("boolean"), BooleanAsdfNode.of(true));
        leafMap.put(StringAsdfNode.of("byte"), NumberAsdfNode.of(0x1F));
        leafMap.put(StringAsdfNode.of("double"), NumberAsdfNode.of(Double.MAX_VALUE));
        leafMap.put(StringAsdfNode.of("float"), NumberAsdfNode.of(Float.MAX_VALUE));
        leafMap.put(StringAsdfNode.of("instant"), TimestampAsdfNode.of(Instant.EPOCH));
        leafMap.put(StringAsdfNode.of("int"), NumberAsdfNode.of(Integer.MAX_VALUE));

        leafMap.put(StringAsdfNode.of("list"), SequenceAsdfNode.of(
                new SequenceNode(Tag.SEQ, Collections.emptyList(), DumperOptions.FlowStyle.FLOW),
                Stream.of("a", "b", "c").map(StringAsdfNode::of).collect(Collectors.toList())
        ));

        leafMap.put(StringAsdfNode.of("long"), NumberAsdfNode.of(Long.MAX_VALUE));

        leafMap.put(StringAsdfNode.of("map"), MappingAsdfNode.of(
                new MappingNode(Tag.MAP, Collections.emptyList(), DumperOptions.FlowStyle.FLOW),
                Stream.of("a", "b", "c").collect(Collectors.toMap(
                        StringAsdfNode::of,
                        v -> StringAsdfNode.of(v + " value")
                ))
        ));

        final NdArrayAsdfNode ndArrayAsdfNode = mock();
        when(ndArrayAsdfNode.get()).thenReturn(ndArrayAsdfNode);
        leafMap.put(StringAsdfNode.of("ndarray"), ndArrayAsdfNode);

        leafMap.put(StringAsdfNode.of("number"), NumberAsdfNode.of(14));
        leafMap.put(StringAsdfNode.of("short"), NumberAsdfNode.of(Short.MAX_VALUE));

        leafNode = MappingAsdfNode.of(
                new MappingNode(Tag.MAP, Collections.emptyList(), DumperOptions.FlowStyle.FLOW),
                leafMap
        );

        sequenceNode = SequenceAsdfNode.of(
                new SequenceNode(Tag.SEQ, Collections.emptyList(), DumperOptions.FlowStyle.FLOW),
                Stream.of(leafNode).collect(Collectors.toList())
        );

        final Map<AsdfNode, AsdfNode> rootMap = new HashMap<>();
        rootMap.put(StringAsdfNode.of("sequence"), sequenceNode);

        rootNode = MappingAsdfNode.of(
                new MappingNode(Tag.MAP, Collections.emptyList(), DumperOptions.FlowStyle.FLOW),
                rootMap
        );
    }

    @Nested
    class ContainsKey {
        @Test
        void testNoArgs() {
            assertTrue(rootNode.containsKey());
        }

        @Test
        void testFirstLevel() {
            assertTrue(rootNode.containsKey("sequence"));
            assertFalse(rootNode.containsKey("missing"));
        }

        @Test
        void testSecondLevel() {
            assertTrue(rootNode.containsKey("sequence", 0));
            assertFalse(rootNode.containsKey("sequence", 1));
        }

        @Test
        void testThirdLevel() {
            assertTrue(rootNode.containsKey("sequence", 0, "string"));
            assertFalse(rootNode.containsKey("sequence", 0, "missing"));
        }
    }

    @Nested
    class Get {
        @Test
        void testNoArgs() {
            assertSame(rootNode, rootNode.get());
        }

        @Test
        void testFirstLevel() {
            assertSame(sequenceNode, rootNode.get("sequence"));
            assertThrows(IllegalArgumentException.class, () -> rootNode.get("missing"));
        }

        @Test
        void testSecondLevel() {
            assertSame(leafNode, rootNode.get("sequence", 0));
            assertThrows(IndexOutOfBoundsException.class, () -> rootNode.get("sequence", 1));
        }

        @Test
        void testThirdLevel() {
            assertSame(leafNode.get("string"), rootNode.get("sequence", 0, "string"));
            assertThrows(IllegalArgumentException.class, () -> rootNode.get("sequence", 0, "missing"));
        }
    }

    @Nested
    class GetOptional {
        @Test
        void testNoArgs() {
            assertEquals(Optional.of(rootNode), rootNode.getOptional());
        }

        @Test
        void testFirstLevel() {
            assertEquals(Optional.of(sequenceNode), rootNode.getOptional("sequence"));
            assertFalse(rootNode.getOptional("missing").isPresent());
        }

        @Test
        void testSecondLevel() {
            assertEquals(Optional.of(leafNode), rootNode.getOptional("sequence", 0));
            assertFalse(rootNode.getOptional("sequence", 1).isPresent());
        }

        @Test
        void testThirdLevel() {
            assertEquals(Optional.of(leafNode.get("string")), rootNode.getOptional("sequence", 0, "string"));
            assertFalse(rootNode.getOptional("sequence", 0, "missing").isPresent());
        }
    }

    @Nested
    class GetTyped {
        @Test
        void testGetBigDecimal() {
            assertEquals(leafNode.getBigDecimal("big decimal"), rootNode.getBigDecimal("sequence", 0, "big decimal"));
        }

        @Test
        void testGetBoolean() {
            assertEquals(leafNode.getBoolean("boolean"), rootNode.getBoolean("sequence", 0, "boolean"));
        }

        @Test
        void testGetByte() {
            assertEquals(leafNode.getByte("byte"), rootNode.getByte("sequence", 0, "byte"));
        }

        @Test
        void testGetDouble() {
            assertEquals(leafNode.getDouble("double"), rootNode.getDouble("sequence", 0, "double"));
        }

        @Test
        void testGetFloat() {
            assertEquals(leafNode.getFloat("float"), rootNode.getFloat("sequence", 0, "float"));
        }

        @Test
        void testGetInstant() {
            assertEquals(leafNode.getInstant("instant"), rootNode.getInstant("sequence", 0, "instant"));
        }

        @Test
        void testGetInt() {
            assertEquals(leafNode.getInt("int"), rootNode.getInt("sequence", 0, "int"));
        }

        @Test
        void testGetList() {
            assertEquals(leafNode.getList(String.class, "list"), rootNode.getList(String.class, "sequence", 0, "list"));
        }

        @Test
        void testGetLong() {
            assertEquals(leafNode.getLong("long"), rootNode.getLong("sequence", 0, "long"));
        }

        @Test
        void testGetMap() {
            assertEquals(leafNode.getMap(String.class, String.class, "map"), rootNode.getMap(String.class, String.class, "sequence", 0, "map"));
        }

        @Test
        void testGetNdArray() {
            assertEquals(leafNode.getNdArray("ndarray"), rootNode.getNdArray("sequence", 0, "ndarray"));
        }

        @Test
        void testGetNumber() {
            assertEquals(leafNode.getNumber("number"), rootNode.getNumber("sequence", 0, "number"));
        }

        @Test
        void testGetShort() {
            assertEquals(leafNode.getShort("short"), rootNode.getShort("sequence", 0, "short"));
        }

        @Test
        void testGetString() {
            assertEquals(leafNode.getString("string"), rootNode.getString("sequence", 0, "string"));
        }
    }

    @Nested
    class KeyTypes {
        @Test
        void testStringKey() {
            assertEquals(leafNode.getString("string"), rootNode.getString("sequence", 0, "string"));
        }

        @Test
        void testBooleanKey() {
            assertEquals(leafNode.getString(true), rootNode.getString("sequence", 0, true));
        }

        @Test
        void testNumberKey() {
            assertEquals(leafNode.getString(1), rootNode.getString("sequence", 0, 1));
        }

        @Test
        void testAsdfNodeKey() {
            assertEquals(leafNode.getString(NumberAsdfNode.of(1)), rootNode.getString("sequence", 0, NumberAsdfNode.of(1)));
        }

        @Test
        void testInvalidKeyType() {
            assertThrows(IllegalArgumentException.class, () -> leafNode.getString(new Object()));
        }
    }
}
