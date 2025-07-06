package org.asdfformat.asdf.node;

import org.asdfformat.asdf.node.impl.BooleanAsdfNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BooleanAsdfNodeTest {
    @Nested
    class ConstructionFromBoolean {
        private BooleanAsdfNode trueNode;
        private BooleanAsdfNode falseNode;

        @BeforeEach
        void beforeEach() {
            trueNode = BooleanAsdfNode.of(true);
            falseNode = BooleanAsdfNode.of(false);
        }

        @Test
        void testTrue() {
            assertTrue(trueNode.asBoolean());
            assertEquals(Tag.BOOL.getValue(), trueNode.getTag());
        }

        @Test
        void testFalse() {
            assertFalse(falseNode.asBoolean());
            assertEquals(Tag.BOOL.getValue(), falseNode.getTag());
        }

        @Test
        void testEqualsAndHashCode() {
            assertNotEquals(trueNode, falseNode);
            assertNotEquals(trueNode.hashCode(), falseNode.hashCode());

            assertEquals(BooleanAsdfNode.of(true), trueNode);
            assertEquals(BooleanAsdfNode.of(true).hashCode(), trueNode.hashCode());

            assertEquals(BooleanAsdfNode.of(false), falseNode);
            assertEquals(BooleanAsdfNode.of(false).hashCode(), falseNode.hashCode());

            assertNotEquals(trueNode, true);
            assertNotEquals(trueNode, null);
            assertEquals(trueNode, trueNode);
        }

        @Test
        void testToString() {
            assertEquals("BooleanAsdfNode(value=true)", trueNode.toString());
            assertEquals("BooleanAsdfNode(value=false)", falseNode.toString());
        }
    }

    @Nested
    class ConstructionFromSnakeYamlNode {
        @ParameterizedTest
        @ValueSource(strings = {"yes", "true", "on"})
        void testTrueValue(final String value) {
            final ScalarNode snakeYamlNode = new ScalarNode(Tag.BOOL, value, null, null, DumperOptions.ScalarStyle.LITERAL);
            final BooleanAsdfNode asdfNode = BooleanAsdfNode.of(snakeYamlNode);

            assertTrue(asdfNode.asBoolean());
            assertEquals(snakeYamlNode.getTag().getValue(), asdfNode.getTag());

            assertEquals(BooleanAsdfNode.of(true), asdfNode);
            assertEquals(BooleanAsdfNode.of(true).hashCode(), asdfNode.hashCode());

            assertEquals(String.format("BooleanAsdfNode(value=%s)", value), asdfNode.toString());
        }

        @ParameterizedTest
        @ValueSource(strings = {"no", "false", "off"})
        void testFalseValue(final String value) {
            final ScalarNode snakeYamlNode = new ScalarNode(Tag.BOOL, value, null, null, DumperOptions.ScalarStyle.LITERAL);
            final BooleanAsdfNode asdfNode = BooleanAsdfNode.of(snakeYamlNode);

            assertFalse(asdfNode.asBoolean());
            assertEquals(snakeYamlNode.getTag().getValue(), asdfNode.getTag());

            assertEquals(BooleanAsdfNode.of(false), asdfNode);
            assertEquals(BooleanAsdfNode.of(false).hashCode(), asdfNode.hashCode());

            assertEquals(String.format("BooleanAsdfNode(value=%s)", value), asdfNode.toString());
        }

        @Test
        void testBogusValue() {
            final ScalarNode snakeYamlNode = new ScalarNode(Tag.BOOL, "maybe", null, null, DumperOptions.ScalarStyle.LITERAL);
            final BooleanAsdfNode asdfNode = BooleanAsdfNode.of(snakeYamlNode);

            assertThrows(RuntimeException.class, asdfNode::asBoolean);
        }

        @Test
        void testCustomTag() {
            final Tag customTag = new Tag("foo");
            final ScalarNode snakeYamlNode = new ScalarNode(customTag, "true", null, null, DumperOptions.ScalarStyle.LITERAL);
            final BooleanAsdfNode asdfNode = BooleanAsdfNode.of(snakeYamlNode);

            assertTrue(asdfNode.asBoolean());
            assertEquals(customTag.getValue(), asdfNode.getTag());

            assertNotEquals(BooleanAsdfNode.of(true), asdfNode);
            assertNotEquals(BooleanAsdfNode.of(true).hashCode(), asdfNode.hashCode());

            assertEquals("BooleanAsdfNode(tag=foo, value=true)", asdfNode.toString());
        }
    }

    @Test
    void testNodeType() {
        assertEquals(AsdfNodeType.BOOLEAN, BooleanAsdfNode.of(true).getNodeType());
    }

    @Test
    void testBaseMethods() {
        final BooleanAsdfNode node = BooleanAsdfNode.of(true);

        assertTrue(node.isBoolean());
        assertFalse(node.isMapping());
        assertFalse(node.isNdArray());
        assertFalse(node.isNull());
        assertFalse(node.isNumber());
        assertFalse(node.isSequence());
        assertFalse(node.isString());
        assertFalse(node.isTimestamp());

        assertFalse(node.containsKey("foo"));
        assertFalse(node.containsKey(1L));
        assertFalse(node.containsKey(false));
        assertFalse(node.containsKey(node));

        assertEquals(0, node.size());

        assertFalse(node.iterator().hasNext());

        assertThrows(IllegalStateException.class, () -> node.get("foo"));
        assertThrows(IllegalStateException.class, () -> node.get(1L));
        assertThrows(IllegalStateException.class, () -> node.get(false));
        assertThrows(IllegalStateException.class, () -> node.get(node));

        assertThrows(IllegalStateException.class, () -> node.getBigDecimal("foo"));
        assertThrows(IllegalStateException.class, () -> node.getBigDecimal(1L));
        assertThrows(IllegalStateException.class, () -> node.getBigDecimal(false));
        assertThrows(IllegalStateException.class, () -> node.getBigDecimal(node));

        assertThrows(IllegalStateException.class, () -> node.getBigInteger("foo"));
        assertThrows(IllegalStateException.class, () -> node.getBigInteger(1L));
        assertThrows(IllegalStateException.class, () -> node.getBigInteger(false));
        assertThrows(IllegalStateException.class, () -> node.getBigInteger(node));

        assertThrows(IllegalStateException.class, () -> node.getBoolean("foo"));
        assertThrows(IllegalStateException.class, () -> node.getBoolean(1L));
        assertThrows(IllegalStateException.class, () -> node.getBoolean(false));
        assertThrows(IllegalStateException.class, () -> node.getBoolean(node));

        assertThrows(IllegalStateException.class, () -> node.getByte("foo"));
        assertThrows(IllegalStateException.class, () -> node.getByte(1L));
        assertThrows(IllegalStateException.class, () -> node.getByte(false));
        assertThrows(IllegalStateException.class, () -> node.getByte(node));

        assertThrows(IllegalStateException.class, () -> node.getDouble("foo"));
        assertThrows(IllegalStateException.class, () -> node.getDouble(1L));
        assertThrows(IllegalStateException.class, () -> node.getDouble(false));
        assertThrows(IllegalStateException.class, () -> node.getDouble(node));

        assertThrows(IllegalStateException.class, () -> node.getFloat("foo"));
        assertThrows(IllegalStateException.class, () -> node.getFloat(1L));
        assertThrows(IllegalStateException.class, () -> node.getFloat(false));
        assertThrows(IllegalStateException.class, () -> node.getFloat(node));

        assertThrows(IllegalStateException.class, () -> node.getInstant("foo"));
        assertThrows(IllegalStateException.class, () -> node.getInstant(1L));
        assertThrows(IllegalStateException.class, () -> node.getInstant(false));
        assertThrows(IllegalStateException.class, () -> node.getInstant(node));

        assertThrows(IllegalStateException.class, () -> node.getInt("foo"));
        assertThrows(IllegalStateException.class, () -> node.getInt(1L));
        assertThrows(IllegalStateException.class, () -> node.getInt(false));
        assertThrows(IllegalStateException.class, () -> node.getInt(node));

        assertThrows(IllegalStateException.class, () -> node.getList("foo", String.class));
        assertThrows(IllegalStateException.class, () -> node.getList(1L, String.class));
        assertThrows(IllegalStateException.class, () -> node.getList(false, String.class));
        assertThrows(IllegalStateException.class, () -> node.getList(node, String.class));

        assertThrows(IllegalStateException.class, () -> node.getLong("foo"));
        assertThrows(IllegalStateException.class, () -> node.getLong(1L));
        assertThrows(IllegalStateException.class, () -> node.getLong(false));
        assertThrows(IllegalStateException.class, () -> node.getLong(node));

        assertThrows(IllegalStateException.class, () -> node.getNumber("foo"));
        assertThrows(IllegalStateException.class, () -> node.getNumber(1L));
        assertThrows(IllegalStateException.class, () -> node.getNumber(false));
        assertThrows(IllegalStateException.class, () -> node.getNumber(node));

        assertThrows(IllegalStateException.class, () -> node.getMap("foo", String.class, String.class));
        assertThrows(IllegalStateException.class, () -> node.getMap(1L, String.class, String.class));
        assertThrows(IllegalStateException.class, () -> node.getMap(false, String.class, String.class));
        assertThrows(IllegalStateException.class, () -> node.getMap(node, String.class, String.class));

        assertThrows(IllegalStateException.class, () -> node.getNdArray("foo"));
        assertThrows(IllegalStateException.class, () -> node.getNdArray(1L));
        assertThrows(IllegalStateException.class, () -> node.getNdArray(false));
        assertThrows(IllegalStateException.class, () -> node.getNdArray(node));

        assertThrows(IllegalStateException.class, () -> node.getShort("foo"));
        assertThrows(IllegalStateException.class, () -> node.getShort(1L));
        assertThrows(IllegalStateException.class, () -> node.getShort(false));
        assertThrows(IllegalStateException.class, () -> node.getShort(node));

        assertThrows(IllegalStateException.class, () -> node.getString("foo"));
        assertThrows(IllegalStateException.class, () -> node.getString(1L));
        assertThrows(IllegalStateException.class, () -> node.getString(false));
        assertThrows(IllegalStateException.class, () -> node.getString(node));

        assertThrows(IllegalStateException.class, node::asBigDecimal);
        assertThrows(IllegalStateException.class, node::asBigInteger);
        assertThrows(IllegalStateException.class, node::asByte);
        assertThrows(IllegalStateException.class, node::asDouble);
        assertThrows(IllegalStateException.class, node::asFloat);
        assertThrows(IllegalStateException.class, node::asInstant);
        assertThrows(IllegalStateException.class, node::asInt);
        assertThrows(IllegalStateException.class, () -> node.asList(String.class));
        assertThrows(IllegalStateException.class, node::asLong);
        assertThrows(IllegalStateException.class, () -> node.asMap(String.class, String.class));
        assertThrows(IllegalStateException.class, node::asNdArray);
        assertThrows(IllegalStateException.class, node::asNumber);
        assertThrows(IllegalStateException.class, node::asShort);
        assertThrows(IllegalStateException.class, node::asString);
    }
}
