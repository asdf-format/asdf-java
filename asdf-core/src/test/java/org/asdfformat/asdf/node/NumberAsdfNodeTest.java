package org.asdfformat.asdf.node;

import org.asdfformat.asdf.node.impl.NumberAsdfNode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumberAsdfNodeTest {
    @Nested
    class ConstructionFromNumber {
        @Test
        void testByte() {
            final Byte value = Byte.MAX_VALUE;
            final NumberAsdfNode node = NumberAsdfNode.of(value);

            assertEquals(value, node.asByte());
            assertEquals(Tag.INT.getValue(), node.getTag());
        }

        @Test
        void testShort() {
            final Short value = Short.MAX_VALUE;
            final NumberAsdfNode node = NumberAsdfNode.of(value);

            assertEquals(value, node.asShort());
            assertEquals(Tag.INT.getValue(), node.getTag());
        }

        @Test
        void testInteger() {
            final Integer value = Integer.MAX_VALUE;
            final NumberAsdfNode node = NumberAsdfNode.of(value);

            assertEquals(value, node.asInt());
            assertEquals(Tag.INT.getValue(), node.getTag());
        }

        @Test
        void testLong() {
            final Long value = Long.MAX_VALUE;
            final NumberAsdfNode node = NumberAsdfNode.of(value);

            assertEquals(value, node.asLong());
            assertEquals(Tag.INT.getValue(), node.getTag());
        }

        @Test
        void testBigInteger() {
            final BigInteger value = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE);
            final NumberAsdfNode node = NumberAsdfNode.of(value);

            assertEquals(value, node.asBigInteger());
            assertEquals(Tag.INT.getValue(), node.getTag());
        }

        @Test
        void testFloat() {
            final Float value = Float.MAX_VALUE;
            final NumberAsdfNode node = NumberAsdfNode.of(value);

            assertEquals(value, node.asFloat());
            assertEquals(Tag.FLOAT.getValue(), node.getTag());
        }

        @Test
        void testDouble() {
            final Double value = Double.MAX_VALUE;
            final NumberAsdfNode node = NumberAsdfNode.of(value);

            assertEquals(value, node.asDouble());
            assertEquals(Tag.FLOAT.getValue(), node.getTag());
        }

        @Test
        void testBigDecimal() {
            final BigDecimal value = BigDecimal.TEN;
            final NumberAsdfNode node = NumberAsdfNode.of(value);

            assertEquals(value, node.asBigDecimal());
            assertEquals(Tag.FLOAT.getValue(), node.getTag());
        }

        @Test
        void testUnhandledNumberSubclass() {
            final AtomicInteger value = new AtomicInteger(10);

            assertThrows(IllegalArgumentException.class, () -> NumberAsdfNode.of(value));
        }
    }

    @Nested
    class ConstructionFromSnakeYamlNode {
        @Test
        void testByte() {
            final Byte value = Byte.MAX_VALUE;
            final ScalarNode scalarNode = new ScalarNode(Tag.INT, value.toString(), null, null, DumperOptions.ScalarStyle.LITERAL);
            final NumberAsdfNode node = NumberAsdfNode.of(scalarNode, value);

            assertEquals(value, node.asByte());
            assertEquals(Tag.INT.getValue(), node.getTag());
        }

        @Test
        void testShort() {
            final Short value = Short.MAX_VALUE;
            final ScalarNode scalarNode = new ScalarNode(Tag.INT, value.toString(), null, null, DumperOptions.ScalarStyle.LITERAL);
            final NumberAsdfNode node = NumberAsdfNode.of(scalarNode, value);

            assertEquals(value, node.asShort());
            assertEquals(Tag.INT.getValue(), node.getTag());
        }

        @Test
        void testInteger() {
            final Integer value = Integer.MAX_VALUE;
            final ScalarNode scalarNode = new ScalarNode(Tag.INT, value.toString(), null, null, DumperOptions.ScalarStyle.LITERAL);
            final NumberAsdfNode node = NumberAsdfNode.of(scalarNode, value);

            assertEquals(value, node.asInt());
            assertEquals(Tag.INT.getValue(), node.getTag());
        }

        @Test
        void testLong() {
            final Long value = Long.MAX_VALUE;
            final ScalarNode scalarNode = new ScalarNode(Tag.INT, value.toString(), null, null, DumperOptions.ScalarStyle.LITERAL);
            final NumberAsdfNode node = NumberAsdfNode.of(scalarNode, value);

            assertEquals(value, node.asLong());
            assertEquals(Tag.INT.getValue(), node.getTag());
        }

        @Test
        void testBigInteger() {
            final BigInteger value = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE);
            final ScalarNode scalarNode = new ScalarNode(Tag.INT, value.toString(), null, null, DumperOptions.ScalarStyle.LITERAL);
            final NumberAsdfNode node = NumberAsdfNode.of(scalarNode, value);

            assertEquals(value, node.asBigInteger());
            assertEquals(Tag.INT.getValue(), node.getTag());
        }

        @Test
        void testFloat() {
            final Float value = Float.MAX_VALUE;
            final ScalarNode scalarNode = new ScalarNode(Tag.FLOAT, value.toString(), null, null, DumperOptions.ScalarStyle.LITERAL);
            final NumberAsdfNode node = NumberAsdfNode.of(scalarNode, value);

            assertEquals(value, node.asFloat());
            assertEquals(Tag.FLOAT.getValue(), node.getTag());
        }

        @Test
        void testDouble() {
            final Double value = Double.MAX_VALUE;
            final ScalarNode scalarNode = new ScalarNode(Tag.FLOAT, value.toString(), null, null, DumperOptions.ScalarStyle.LITERAL);
            final NumberAsdfNode node = NumberAsdfNode.of(scalarNode, value);

            assertEquals(value, node.asDouble());
            assertEquals(Tag.FLOAT.getValue(), node.getTag());
        }

        @Test
        void testBigDecimal() {
            final BigDecimal value = BigDecimal.TEN;
            final ScalarNode scalarNode = new ScalarNode(Tag.FLOAT, value.toString(), null, null, DumperOptions.ScalarStyle.LITERAL);
            final NumberAsdfNode node = NumberAsdfNode.of(scalarNode, value);

            assertEquals(value, node.asBigDecimal());
            assertEquals(Tag.FLOAT.getValue(), node.getTag());
        }

        @Test
        void testCustomTag() {
            final Integer value = 1;
            final Tag customTag = new Tag("custom");
            final ScalarNode snakeYamlNode = new ScalarNode(customTag, value.toString(), null, null, DumperOptions.ScalarStyle.LITERAL);
            final NumberAsdfNode asdfNode = NumberAsdfNode.of(snakeYamlNode, value);

            assertEquals(value, asdfNode.asInt());
            assertEquals(customTag.getValue(), asdfNode.getTag());

            assertEquals(NumberAsdfNode.of(snakeYamlNode, value), asdfNode);
            assertNotEquals(NumberAsdfNode.of(1), asdfNode);

            assertEquals("NumberAsdfNode(tag=custom, value=1)", asdfNode.toString());
        }
    }

    @Nested
    class EqualsAndHashCode {
        @Test
        void testAgainstNull() {
            final NumberAsdfNode node = NumberAsdfNode.of(1L);

            assertNotEquals(node, null);
        }

        @Test
        void testAgainstUnwrappedNumber() {
            final NumberAsdfNode node = NumberAsdfNode.of(1L);

            assertNotEquals(node, 1L);
        }

        @Test
        void testAgainstSelf() {
            final NumberAsdfNode node = NumberAsdfNode.of(1L);

            assertEquals(node, node);
        }

        @Test
        void testSameValueClass() {
            final NumberAsdfNode node = NumberAsdfNode.of(1L);

            assertEquals(node, NumberAsdfNode.of(1L));
            assertEquals(node.hashCode(), NumberAsdfNode.of(1L).hashCode());

            assertNotEquals(node, NumberAsdfNode.of(2L));
            assertNotEquals(node.hashCode(), NumberAsdfNode.of(2L).hashCode());
        }

        @Test
        void testDifferentIntegerValueClass() {
            final NumberAsdfNode node = NumberAsdfNode.of(1L);

            assertEquals(node, NumberAsdfNode.of(BigInteger.ONE));
            assertEquals(node.hashCode(), NumberAsdfNode.of(BigInteger.ONE).hashCode());

            assertNotEquals(node, NumberAsdfNode.of(BigInteger.TEN));
            assertNotEquals(node.hashCode(), NumberAsdfNode.of(BigInteger.TEN).hashCode());
        }

        @Test
        void testDifferentDecimalValueClass() {
            final NumberAsdfNode node = NumberAsdfNode.of(3.14159);

            assertEquals(node, NumberAsdfNode.of(new BigDecimal("3.14159")));
            assertEquals(node.hashCode(), NumberAsdfNode.of(new BigDecimal("3.14159")).hashCode());

            assertNotEquals(node, NumberAsdfNode.of(new BigDecimal("2.71828")));
            assertNotEquals(node.hashCode(), NumberAsdfNode.of(new BigDecimal("2.71828")).hashCode());
        }

        @Test
        void testSpecialFloatingPointValues() {
            final NumberAsdfNode nanNode = NumberAsdfNode.of(Double.NaN);
            final NumberAsdfNode positiveInfinityNode = NumberAsdfNode.of(Double.POSITIVE_INFINITY);
            final NumberAsdfNode negativeInfinityNode = NumberAsdfNode.of(Double.NEGATIVE_INFINITY);

            assertEquals(nanNode, NumberAsdfNode.of(Float.NaN));
            assertEquals(nanNode.hashCode(), NumberAsdfNode.of(Float.NaN).hashCode());

            assertNotEquals(nanNode, positiveInfinityNode);
            assertNotEquals(nanNode.hashCode(), positiveInfinityNode.hashCode());

            assertEquals(positiveInfinityNode, NumberAsdfNode.of(Float.POSITIVE_INFINITY));
            assertEquals(positiveInfinityNode.hashCode(), NumberAsdfNode.of(Float.POSITIVE_INFINITY).hashCode());

            assertNotEquals(positiveInfinityNode, negativeInfinityNode);
            assertNotEquals(positiveInfinityNode.hashCode(), negativeInfinityNode.hashCode());

            assertEquals(negativeInfinityNode, NumberAsdfNode.of(Float.NEGATIVE_INFINITY));
            assertEquals(negativeInfinityNode.hashCode(), NumberAsdfNode.of(Float.NEGATIVE_INFINITY).hashCode());

            assertNotEquals(negativeInfinityNode, nanNode);
            assertNotEquals(negativeInfinityNode.hashCode(), nanNode.hashCode());
        }
    }

    @Test
    void testConversionFromByte() {
        final byte value = Byte.MAX_VALUE;
        final NumberAsdfNode node = NumberAsdfNode.of(value);

        assertEquals(value, node.asByte());
        assertEquals(value, node.asShort());
        assertEquals(value, node.asInt());
        assertEquals(value, node.asLong());
        assertEquals(BigInteger.valueOf(value), node.asBigInteger());
        assertEquals(value, node.asFloat());
        assertEquals(value, node.asDouble());
        assertEquals(BigDecimal.valueOf(value), node.asBigDecimal());

        assertEquals(value, node.asNumber());
    }

    @Test
    void testConversionFromShort() {
        final short value = Short.MAX_VALUE;
        final NumberAsdfNode node = NumberAsdfNode.of(value);

        assertThrows(IllegalStateException.class, node::asByte);
        assertEquals(value, node.asShort());
        assertEquals(value, node.asInt());
        assertEquals(value, node.asLong());
        assertEquals(BigInteger.valueOf(value), node.asBigInteger());
        assertEquals(value, node.asFloat());
        assertEquals(value, node.asDouble());
        assertEquals(BigDecimal.valueOf(value), node.asBigDecimal());

        assertEquals(value, node.asNumber());
    }

    @Test
    void testConversionFromInteger() {
        final int value = Integer.MAX_VALUE;
        final NumberAsdfNode node = NumberAsdfNode.of(value);

        assertThrows(IllegalStateException.class, node::asByte);
        assertThrows(IllegalStateException.class, node::asShort);
        assertEquals(value, node.asInt());
        assertEquals(value, node.asLong());
        assertEquals(BigInteger.valueOf(value), node.asBigInteger());
        assertEquals(value, node.asFloat());
        assertEquals(value, node.asDouble());
        assertEquals(BigDecimal.valueOf(value), node.asBigDecimal());

        assertEquals(value, node.asNumber());
    }

    @Test
    void testConversionFromLong() {
        final long value = Long.MAX_VALUE;
        final NumberAsdfNode node = NumberAsdfNode.of(value);

        assertThrows(IllegalStateException.class, node::asByte);
        assertThrows(IllegalStateException.class, node::asShort);
        assertThrows(IllegalStateException.class, node::asInt);
        assertEquals(value, node.asLong());
        assertEquals(BigInteger.valueOf(value), node.asBigInteger());
        assertEquals(value, node.asFloat());
        assertEquals(value, node.asDouble());
        assertEquals(BigDecimal.valueOf(value), node.asBigDecimal());

        assertEquals(value, node.asNumber());
    }

    @Test
    void testConversionFromBigInteger() {
        final BigInteger value = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE);
        final NumberAsdfNode node = NumberAsdfNode.of(value);

        assertThrows(IllegalStateException.class, node::asByte);
        assertThrows(IllegalStateException.class, node::asShort);
        assertThrows(IllegalStateException.class, node::asInt);
        assertThrows(IllegalStateException.class, node::asLong);
        assertEquals(value, node.asBigInteger());
        assertEquals(value.floatValue(), node.asFloat());
        assertEquals(value.doubleValue(), node.asDouble());
        assertEquals(new BigDecimal(value), node.asBigDecimal());

        assertEquals(value, node.asNumber());
    }

    @Test
    void testConversionFromFloat() {
        final float value = Float.MAX_VALUE;
        final NumberAsdfNode node = NumberAsdfNode.of(value);

        assertThrows(IllegalStateException.class, node::asByte);
        assertThrows(IllegalStateException.class, node::asShort);
        assertThrows(IllegalStateException.class, node::asInt);
        assertThrows(IllegalStateException.class, node::asLong);
        assertThrows(IllegalStateException.class, node::asBigInteger);
        assertEquals(value, node.asFloat());
        assertEquals(value, node.asDouble());
        assertEquals(BigDecimal.valueOf(value), node.asBigDecimal());

        assertEquals(value, node.asNumber());
    }

    @Test
    void testConversionFromDouble() {
        final double value = Double.MAX_VALUE;
        final NumberAsdfNode node = NumberAsdfNode.of(value);

        assertThrows(IllegalStateException.class, node::asByte);
        assertThrows(IllegalStateException.class, node::asShort);
        assertThrows(IllegalStateException.class, node::asInt);
        assertThrows(IllegalStateException.class, node::asLong);
        assertThrows(IllegalStateException.class, node::asBigInteger);
        assertEquals((float)value, node.asFloat());
        assertEquals(value, node.asDouble());
        assertEquals(BigDecimal.valueOf(value), node.asBigDecimal());

        assertEquals(value, node.asNumber());
    }

    @Test
    void testConversionFromBigDecimal() {
        final BigDecimal value = BigDecimal.valueOf(3.14159);
        final NumberAsdfNode node = NumberAsdfNode.of(value);

        assertThrows(IllegalStateException.class, node::asByte);
        assertThrows(IllegalStateException.class, node::asShort);
        assertThrows(IllegalStateException.class, node::asInt);
        assertThrows(IllegalStateException.class, node::asLong);
        assertThrows(IllegalStateException.class, node::asBigInteger);
        assertEquals(value.floatValue(), node.asFloat());
        assertEquals(value.doubleValue(), node.asDouble());
        assertEquals(value, node.asBigDecimal());

        assertEquals(value, node.asNumber());
    }

    @Test
    void testNodeType() {
        assertEquals(AsdfNodeType.NUMBER, NumberAsdfNode.of(1).getNodeType());
    }

    @Test
    void testToString() {
        assertEquals("NumberAsdfNode(value=1)", NumberAsdfNode.of(1L).toString());
        assertEquals("NumberAsdfNode(value=1.0)", NumberAsdfNode.of(1.0f).toString());
    }

    @Test
    void testBaseMethods() {
        final NumberAsdfNode node = NumberAsdfNode.of(1);

        assertFalse(node.isBoolean());
        assertFalse(node.isMapping());
        assertFalse(node.isNdArray());
        assertFalse(node.isNull());
        assertTrue(node.isNumber());
        assertFalse(node.isSequence());
        assertFalse(node.isString());
        assertFalse(node.isTimestamp());

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
        assertEquals(BigDecimal.ONE, node.getBigDecimal());

        assertThrows(IllegalStateException.class, () -> node.getBigInteger("foo"));
        assertThrows(IllegalStateException.class, () -> node.getBigInteger(1L));
        assertThrows(IllegalStateException.class, () -> node.getBigInteger(false));
        assertThrows(IllegalStateException.class, () -> node.getBigInteger(node));
        assertThrows(IllegalStateException.class, () -> node.getBigInteger("foo", 1L, false, node));
        assertEquals(BigInteger.ONE, node.getBigInteger());

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
        assertEquals(1, node.getByte());

        assertThrows(IllegalStateException.class, () -> node.getDouble("foo"));
        assertThrows(IllegalStateException.class, () -> node.getDouble(1L));
        assertThrows(IllegalStateException.class, () -> node.getDouble(false));
        assertThrows(IllegalStateException.class, () -> node.getDouble(node));
        assertThrows(IllegalStateException.class, () -> node.getDouble("foo", 1L, false, node));
        assertEquals(1.0, node.getDouble());

        assertThrows(IllegalStateException.class, () -> node.getFloat("foo"));
        assertThrows(IllegalStateException.class, () -> node.getFloat(1L));
        assertThrows(IllegalStateException.class, () -> node.getFloat(false));
        assertThrows(IllegalStateException.class, () -> node.getFloat(node));
        assertThrows(IllegalStateException.class, () -> node.getFloat("foo", 1L, false, node));
        assertEquals(1.0, node.getFloat());

        assertThrows(IllegalStateException.class, () -> node.getInstant("foo"));
        assertThrows(IllegalStateException.class, () -> node.getInstant(1L));
        assertThrows(IllegalStateException.class, () -> node.getInstant(false));
        assertThrows(IllegalStateException.class, () -> node.getInstant(node));
        assertThrows(IllegalStateException.class, () -> node.getInstant("foo", 1L, false, node));
        assertThrows(IllegalStateException.class, node::getInstant);

        assertThrows(IllegalStateException.class, () -> node.getInt("foo"));
        assertThrows(IllegalStateException.class, () -> node.getInt(1L));
        assertThrows(IllegalStateException.class, () -> node.getInt(false));
        assertThrows(IllegalStateException.class, () -> node.getInt(node));
        assertThrows(IllegalStateException.class, () -> node.getInt("foo", 1L, false, node));
        assertEquals(1, node.getInt());

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
        assertEquals(1, node.getLong());

        assertThrows(IllegalStateException.class, () -> node.getNumber("foo"));
        assertThrows(IllegalStateException.class, () -> node.getNumber(1L));
        assertThrows(IllegalStateException.class, () -> node.getNumber(false));
        assertThrows(IllegalStateException.class, () -> node.getNumber(node));
        assertThrows(IllegalStateException.class, () -> node.getNumber("foo", 1L, false, node));
        assertEquals(1, node.getNumber());

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
        assertEquals(1, node.getShort());

        assertThrows(IllegalStateException.class, () -> node.getString("foo"));
        assertThrows(IllegalStateException.class, () -> node.getString(1L));
        assertThrows(IllegalStateException.class, () -> node.getString(false));
        assertThrows(IllegalStateException.class, () -> node.getString(node));
        assertThrows(IllegalStateException.class, () -> node.getString("foo", 1L, false, node));
        assertThrows(IllegalStateException.class, node::getString);

        assertThrows(IllegalStateException.class, node::asBoolean);
        assertThrows(IllegalStateException.class, node::asInstant);
        assertThrows(IllegalStateException.class, () -> node.asList(String.class));
        assertThrows(IllegalStateException.class, () -> node.asMap(String.class, String.class));
        assertThrows(IllegalStateException.class, node::asNdArray);
    }
}
