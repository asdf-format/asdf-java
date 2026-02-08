package org.asdfformat.asdf.node;

import org.asdfformat.asdf.node.impl.TimestampAsdfNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimestampAsdfNodeTest {
    @Nested
    class ConstructionFromString {
        private Instant instant ;
        private TimestampAsdfNode node;

        @BeforeEach
        void beforeEach() {
            instant = Instant.now();
            node = TimestampAsdfNode.of(instant);
        }

        @Test
        void testAsInstant() {
            assertEquals(instant, node.asInstant());
        }

        @Test
        void testGetTag() {
            assertEquals(Tag.TIMESTAMP.getValue(), node.getTag());
        }

        @Test
        void testEqualsAndHashCode() {
            assertEquals(TimestampAsdfNode.of(instant), node);
            assertEquals(TimestampAsdfNode.of(instant).hashCode(), node.hashCode());

            assertNotEquals(TimestampAsdfNode.of(Instant.now().minus(1, ChronoUnit.SECONDS)), node);
            assertNotEquals(TimestampAsdfNode.of(Instant.now().minus(1, ChronoUnit.SECONDS)).hashCode(), node.hashCode());

            assertNotEquals(node, instant);
            assertNotEquals(node, null);
            assertEquals(node, node);
        }

        @Test
        void testToString() {
            assertEquals(String.format("TimestampAsdfNode(value=%s)", instant), node.toString());
        }
    }

    @Nested
    class ConstructionFromSnakeYamlNode {
        final String value = "2001-12-15T02:59:43.100Z";
        final Instant instant = Instant.parse(value);

        @Test
        void testBasic() {
            final ScalarNode snakeYamlNode = new ScalarNode(Tag.TIMESTAMP, value, null, null, DumperOptions.ScalarStyle.LITERAL);
            final TimestampAsdfNode asdfNode = TimestampAsdfNode.of(snakeYamlNode, instant);

            assertEquals(instant, asdfNode.asInstant());
            assertEquals(snakeYamlNode.getTag().getValue(), asdfNode.getTag());

            assertEquals(TimestampAsdfNode.of(snakeYamlNode, instant), asdfNode);

            assertEquals(String.format("TimestampAsdfNode(value=%s)", value), asdfNode.toString());
        }

        @Test
        void testCustomTag() {
            final Tag customTag = new Tag("custom");
            final ScalarNode snakeYamlNode = new ScalarNode(customTag, value, null, null, DumperOptions.ScalarStyle.LITERAL);
            final TimestampAsdfNode asdfNode = TimestampAsdfNode.of(snakeYamlNode, instant);

            assertEquals(instant, asdfNode.asInstant());
            assertEquals(customTag.getValue(), asdfNode.getTag());

            assertEquals(TimestampAsdfNode.of(snakeYamlNode, instant), asdfNode);
            assertNotEquals(TimestampAsdfNode.of(instant), asdfNode);

            assertEquals(String.format("TimestampAsdfNode(tag=custom, value=%s)", value), asdfNode.toString());
        }
    }

    @Test
    void testNodeType() {
        assertEquals(AsdfNodeType.TIMESTAMP, TimestampAsdfNode.of(Instant.now()).getNodeType());
    }

    @Test
    void testBaseMethods() {
        final Instant instant = Instant.now();
        final TimestampAsdfNode node = TimestampAsdfNode.of(instant);

        assertFalse(node.isBoolean());
        assertFalse(node.isMapping());
        assertFalse(node.isNdArray());
        assertFalse(node.isNull());
        assertFalse(node.isNumber());
        assertFalse(node.isSequence());
        assertFalse(node.isString());
        assertTrue(node.isTimestamp());

        assertFalse(node.containsKey("foo"));
        assertFalse(node.containsKey(1L));
        assertFalse(node.containsKey(false));
        assertFalse(node.containsKey(node));
        assertFalse(node.containsKey("foo", 1L, false, node));
        assertTrue(node.containsKey());

        assertEquals(0, node.size());

        assertFalse(node.iterator().hasNext());

        assertThrows(IllegalStateException.class, () -> node.get("foo"));
        assertThrows(IllegalStateException.class, () -> node.get(1L));
        assertThrows(IllegalStateException.class, () -> node.get(false));
        assertThrows(IllegalStateException.class, () -> node.get(node));
        assertThrows(IllegalStateException.class, () -> node.get("foo", 1L, false, node));
        assertSame(node, node.get());

        assertThrows(IllegalStateException.class, () -> node.getBigDecimal("foo"));
        assertThrows(IllegalStateException.class, () -> node.getBigDecimal(1L));
        assertThrows(IllegalStateException.class, () -> node.getBigDecimal(false));
        assertThrows(IllegalStateException.class, () -> node.getBigDecimal(node));
        assertThrows(IllegalStateException.class, () -> node.getBigDecimal("foo", 1L, false, node));
        assertThrows(IllegalStateException.class, node::getBigDecimal);

        assertThrows(IllegalStateException.class, () -> node.getBigInteger("foo"));
        assertThrows(IllegalStateException.class, () -> node.getBigInteger(1L));
        assertThrows(IllegalStateException.class, () -> node.getBigInteger(false));
        assertThrows(IllegalStateException.class, () -> node.getBigInteger(node));
        assertThrows(IllegalStateException.class, () -> node.getBigInteger("foo", 1L, false, node));
        assertThrows(IllegalStateException.class, node::getBigInteger);

        assertThrows(IllegalStateException.class, () -> node.getBoolean("foo"));
        assertThrows(IllegalStateException.class, () -> node.getBoolean(1L));
        assertThrows(IllegalStateException.class, () -> node.getBoolean(false));
        assertThrows(IllegalStateException.class, () -> node.getBoolean(node));
        assertThrows(IllegalStateException.class, () -> node.getBoolean("foo", 1L, false, node));
        assertThrows(IllegalStateException.class, node::getBoolean);

        assertThrows(IllegalStateException.class, () -> node.getByte("foo"));
        assertThrows(IllegalStateException.class, () -> node.getByte(1L));
        assertThrows(IllegalStateException.class, () -> node.getByte(false));
        assertThrows(IllegalStateException.class, () -> node.getByte(node));
        assertThrows(IllegalStateException.class, () -> node.getByte("foo", 1L, false, node));
        assertThrows(IllegalStateException.class, node::getByte);

        assertThrows(IllegalStateException.class, () -> node.getDouble("foo"));
        assertThrows(IllegalStateException.class, () -> node.getDouble(1L));
        assertThrows(IllegalStateException.class, () -> node.getDouble(false));
        assertThrows(IllegalStateException.class, () -> node.getDouble(node));
        assertThrows(IllegalStateException.class, () -> node.getDouble("foo", 1L, false, node));
        assertThrows(IllegalStateException.class, node::getDouble);

        assertThrows(IllegalStateException.class, () -> node.getFloat("foo"));
        assertThrows(IllegalStateException.class, () -> node.getFloat(1L));
        assertThrows(IllegalStateException.class, () -> node.getFloat(false));
        assertThrows(IllegalStateException.class, () -> node.getFloat(node));
        assertThrows(IllegalStateException.class, () -> node.getFloat("foo", 1L, false, node));
        assertThrows(IllegalStateException.class, node::getFloat);

        assertThrows(IllegalStateException.class, () -> node.getInstant("foo"));
        assertThrows(IllegalStateException.class, () -> node.getInstant(1L));
        assertThrows(IllegalStateException.class, () -> node.getInstant(false));
        assertThrows(IllegalStateException.class, () -> node.getInstant(node));
        assertThrows(IllegalStateException.class, () -> node.getInstant("foo", 1L, false, node));
        assertEquals(instant, node.getInstant());

        assertThrows(IllegalStateException.class, () -> node.getInt("foo"));
        assertThrows(IllegalStateException.class, () -> node.getInt(1L));
        assertThrows(IllegalStateException.class, () -> node.getInt(false));
        assertThrows(IllegalStateException.class, () -> node.getInt(node));
        assertThrows(IllegalStateException.class, () -> node.getInt("foo", 1L, false, node));
        assertThrows(IllegalStateException.class, node::getInt);

        assertThrows(IllegalStateException.class, () -> node.getList(String.class, "foo"));
        assertThrows(IllegalStateException.class, () -> node.getList(String.class, 1L));
        assertThrows(IllegalStateException.class, () -> node.getList(String.class, false));
        assertThrows(IllegalStateException.class, () -> node.getList(String.class, node));
        assertThrows(IllegalStateException.class, () -> node.getList(String.class, "foo", 1L, false, node));
        assertThrows(IllegalStateException.class, () -> node.getList(String.class));

        assertThrows(IllegalStateException.class, () -> node.getLong("foo"));
        assertThrows(IllegalStateException.class, () -> node.getLong(1L));
        assertThrows(IllegalStateException.class, () -> node.getLong(false));
        assertThrows(IllegalStateException.class, () -> node.getLong(node));
        assertThrows(IllegalStateException.class, () -> node.getLong("foo", 1L, false, node));
        assertThrows(IllegalStateException.class, node::getLong);

        assertThrows(IllegalStateException.class, () -> node.getNumber("foo"));
        assertThrows(IllegalStateException.class, () -> node.getNumber(1L));
        assertThrows(IllegalStateException.class, () -> node.getNumber(false));
        assertThrows(IllegalStateException.class, () -> node.getNumber(node));
        assertThrows(IllegalStateException.class, () -> node.getNumber("foo", 1L, false, node));
        assertThrows(IllegalStateException.class, node::getNumber);

        assertThrows(IllegalStateException.class, () -> node.getMap(String.class, String.class, "foo"));
        assertThrows(IllegalStateException.class, () -> node.getMap(String.class, String.class, 1L));
        assertThrows(IllegalStateException.class, () -> node.getMap(String.class, String.class, false));
        assertThrows(IllegalStateException.class, () -> node.getMap(String.class, String.class, node));
        assertThrows(IllegalStateException.class, () -> node.getMap(String.class, String.class, "foo", 1L, false, node));
        assertThrows(IllegalStateException.class, () -> node.getMap(String.class, String.class));

        assertThrows(IllegalStateException.class, () -> node.getNdArray("foo"));
        assertThrows(IllegalStateException.class, () -> node.getNdArray(1L));
        assertThrows(IllegalStateException.class, () -> node.getNdArray(false));
        assertThrows(IllegalStateException.class, () -> node.getNdArray(node));
        assertThrows(IllegalStateException.class, () -> node.getNdArray("foo", 1L, false, node));
        assertThrows(IllegalStateException.class, node::getNdArray);

        assertThrows(IllegalStateException.class, () -> node.getShort("foo"));
        assertThrows(IllegalStateException.class, () -> node.getShort(1L));
        assertThrows(IllegalStateException.class, () -> node.getShort(false));
        assertThrows(IllegalStateException.class, () -> node.getShort(node));
        assertThrows(IllegalStateException.class, () -> node.getShort("foo", 1L, false, node));
        assertThrows(IllegalStateException.class, node::getShort);

        assertThrows(IllegalStateException.class, () -> node.getString("foo"));
        assertThrows(IllegalStateException.class, () -> node.getString(1L));
        assertThrows(IllegalStateException.class, () -> node.getString(false));
        assertThrows(IllegalStateException.class, () -> node.getString(node));
        assertThrows(IllegalStateException.class, () -> node.getString("foo", 1L, false, node));
        assertThrows(IllegalStateException.class, node::getString);

        assertThrows(IllegalStateException.class, node::asBigDecimal);
        assertThrows(IllegalStateException.class, node::asBigInteger);
        assertThrows(IllegalStateException.class, node::asBoolean);
        assertThrows(IllegalStateException.class, node::asByte);
        assertThrows(IllegalStateException.class, node::asDouble);
        assertThrows(IllegalStateException.class, node::asFloat);
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
