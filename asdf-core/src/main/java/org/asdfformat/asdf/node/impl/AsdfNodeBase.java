package org.asdfformat.asdf.node.impl;

import org.asdfformat.asdf.ndarray.NdArray;
import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.node.AsdfNodeType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AsdfNodeBase implements AsdfNode {

    @Override
    public boolean isBoolean() {
        return getNodeType() == AsdfNodeType.BOOLEAN;
    }

    @Override
    public boolean isMapping() {
        return getNodeType() == AsdfNodeType.MAPPING;
    }

    @Override
    public boolean isNdArray() {
        return getNodeType() == AsdfNodeType.NDARRAY;
    }

    @Override
    public boolean isNull() {
        return getNodeType() == AsdfNodeType.NULL;
    }

    @Override
    public boolean isNumber() {
        return getNodeType() == AsdfNodeType.NUMBER;
    }

    @Override
    public boolean isSequence() {
        return getNodeType() == AsdfNodeType.SEQUENCE;
    }

    @Override
    public boolean isString() {
        return getNodeType() == AsdfNodeType.STRING;
    }

    @Override
    public boolean isTimestamp() {
        return getNodeType() == AsdfNodeType.TIMESTAMP;
    }

    @Override
    public boolean containsKey(final String key) {
        return containsKey(StringAsdfNode.of(key));
    }

    @Override
    public boolean containsKey(final long key) {
        return containsKey(NumberAsdfNode.of(key));
    }

    @Override
    public boolean containsKey(final boolean key) {
        return containsKey(BooleanAsdfNode.of(key));
    }

    @Override
    public boolean containsKey(final AsdfNode key) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public AsdfNode get(final String key) {
        throw new IllegalStateException(makeGetErrorMessage("String"));
    }

    @Override
    public AsdfNode get(final long key) {
        throw new IllegalStateException(makeGetErrorMessage("long"));
    }

    @Override
    public AsdfNode get(final boolean key) {
        throw new IllegalStateException(makeGetErrorMessage("boolean"));
    }

    @Override
    public AsdfNode get(final AsdfNode key) {
        throw new IllegalStateException(makeGetErrorMessage("AsdfNode"));
    }

    @Override
    public Optional<AsdfNode> getOptional(final String key) {
        if (containsKey(key)) {
            return Optional.of(get(key));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AsdfNode> getOptional(final long key) {
        if (containsKey(key)) {
            return Optional.of(get(key));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AsdfNode> getOptional(final boolean key) {
        if (containsKey(key)) {
            return Optional.of(get(key));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AsdfNode> getOptional(final AsdfNode key) {
        if (containsKey(key)) {
            return Optional.of(get(key));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public BigDecimal getBigDecimal(final String key) {
        return get(key).asBigDecimal();
    }

    @Override
    public BigDecimal getBigDecimal(final long key) {
        return get(key).asBigDecimal();
    }

    @Override
    public BigDecimal getBigDecimal(final boolean key) {
        return get(key).asBigDecimal();
    }

    @Override
    public BigDecimal getBigDecimal(final AsdfNode key) {
        return get(key).asBigDecimal();
    }

    @Override
    public BigInteger getBigInteger(final String key) {
        return get(key).asBigInteger();
    }

    @Override
    public BigInteger getBigInteger(final long key) {
        return get(key).asBigInteger();
    }

    @Override
    public BigInteger getBigInteger(final boolean key) {
        return get(key).asBigInteger();
    }

    @Override
    public BigInteger getBigInteger(final AsdfNode key) {
        return get(key).asBigInteger();
    }

    @Override
    public boolean getBoolean(final String key) {
        return get(key).asBoolean();
    }

    @Override
    public boolean getBoolean(final long key) {
        return get(key).asBoolean();
    }

    @Override
    public boolean getBoolean(final boolean key) {
        return get(key).asBoolean();
    }

    @Override
    public boolean getBoolean(final AsdfNode key) {
        return get(key).asBoolean();
    }

    @Override
    public byte getByte(final String key) {
        return get(key).asByte();
    }

    @Override
    public byte getByte(final long key) {
        return get(key).asByte();
    }

    @Override
    public byte getByte(final boolean key) {
        return get(key).asByte();
    }

    @Override
    public byte getByte(final AsdfNode key) {
        return get(key).asByte();
    }

    @Override
    public double getDouble(final String key) {
        return get(key).asDouble();
    }

    @Override
    public double getDouble(final long key) {
        return get(key).asDouble();
    }

    @Override
    public double getDouble(final boolean key) {
        return get(key).asDouble();
    }

    @Override
    public double getDouble(final AsdfNode key) {
        return get(key).asDouble();
    }

    @Override
    public float getFloat(final String key) {
        return get(key).asFloat();
    }

    @Override
    public float getFloat(final long key) {
        return get(key).asFloat();
    }

    @Override
    public float getFloat(final boolean key) {
        return get(key).asFloat();
    }

    @Override
    public float getFloat(final AsdfNode key) {
        return get(key).asFloat();
    }

    @Override
    public Instant getInstant(final String key) {
        return get(key).asInstant();
    }

    @Override
    public Instant getInstant(final long key) {
        return get(key).asInstant();
    }

    @Override
    public Instant getInstant(final boolean key) {
        return get(key).asInstant();
    }

    @Override
    public Instant getInstant(final AsdfNode key) {
        return get(key).asInstant();
    }

    @Override
    public int getInt(final String key) {
        return get(key).asInt();
    }

    @Override
    public int getInt(final long key) {
        return get(key).asInt();
    }

    @Override
    public int getInt(final boolean key) {
        return get(key).asInt();
    }

    @Override
    public int getInt(final AsdfNode key) {
        return get(key).asInt();
    }

    @Override
    public <T> List<T> getList(final String key, final Class<T> elementClass) {
        return get(key).asList(elementClass);
    }

    @Override
    public <T> List<T> getList(final long key, final Class<T> elementClass) {
        return get(key).asList(elementClass);
    }

    @Override
    public <T> List<T> getList(final boolean key, final Class<T> elementClass) {
        return get(key).asList(elementClass);
    }

    @Override
    public <T> List<T> getList(final AsdfNode key, final Class<T> elementClass) {
        return get(key).asList(elementClass);
    }

    @Override
    public long getLong(final String key) {
        return get(key).asLong();
    }

    @Override
    public long getLong(final long key) {
        return get(key).asLong();
    }

    @Override
    public long getLong(final boolean key) {
        return get(key).asLong();
    }

    @Override
    public long getLong(final AsdfNode key) {
        return get(key).asLong();
    }

    @Override
    public Number getNumber(final String key) {
        return get(key).asNumber();
    }

    @Override
    public Number getNumber(final long key) {
        return get(key).asNumber();
    }

    @Override
    public Number getNumber(final boolean key) {
        return get(key).asNumber();
    }

    @Override
    public Number getNumber(final AsdfNode key) {
        return get(key).asNumber();
    }

    @Override
    public <K, V> Map<K, V> getMap(final String key, final Class<K> keyClass, final Class<V> valueClass) {
        return get(key).asMap(keyClass, valueClass);
    }

    @Override
    public <K, V> Map<K, V> getMap(final long key, final Class<K> keyClass, final Class<V> valueClass) {
        return get(key).asMap(keyClass, valueClass);
    }

    @Override
    public <K, V> Map<K, V> getMap(final boolean key, final Class<K> keyClass, final Class<V> valueClass) {
        return get(key).asMap(keyClass, valueClass);
    }

    @Override
    public <K, V> Map<K, V> getMap(final AsdfNode key, final Class<K> keyClass, final Class<V> valueClass) {
        return get(key).asMap(keyClass, valueClass);
    }

    @Override
    public NdArray<?> getNdArray(final String key) {
        return get(key).asNdArray();
    }

    @Override
    public NdArray<?> getNdArray(final long key) {
        return get(key).asNdArray();
    }

    @Override
    public NdArray<?> getNdArray(final boolean key) {
        return get(key).asNdArray();
    }

    @Override
    public NdArray<?> getNdArray(final AsdfNode key) {
        return get(key).asNdArray();
    }

    @Override
    public short getShort(final String key) {
        return get(key).asShort();
    }

    @Override
    public short getShort(final long key) {
        return get(key).asShort();
    }

    @Override
    public short getShort(final boolean key) {
        return get(key).asShort();
    }

    @Override
    public short getShort(final AsdfNode key) {
        return get(key).asShort();
    }

    @Override
    public String getString(final String key) {
        return get(key).asString();
    }

    @Override
    public String getString(final long key) {
        return get(key).asString();
    }

    @Override
    public String getString(final boolean key) {
        return get(key).asString();
    }

    @Override
    public String getString(final AsdfNode key) {
        return get(key).asString();
    }

    @Override
    public BigDecimal asBigDecimal() {
        throw new IllegalStateException(makeAsErrorMessage("BigDecimal"));
    }

    @Override
    public BigInteger asBigInteger() {
        throw new IllegalStateException(makeAsErrorMessage("BigInteger"));

    }

    @Override
    public boolean asBoolean() {
        throw new IllegalStateException(makeAsErrorMessage("boolean"));

    }

    @Override
    public byte asByte() {
        throw new IllegalStateException(makeAsErrorMessage("byte"));

    }

    @Override
    public double asDouble() {
        throw new IllegalStateException(makeAsErrorMessage("double"));

    }

    @Override
    public float asFloat() {
        throw new IllegalStateException(makeAsErrorMessage("float"));

    }

    @Override
    public Instant asInstant() {
        throw new IllegalStateException(makeAsErrorMessage("Instant"));
    }

    @Override
    public int asInt() {
        throw new IllegalStateException(makeAsErrorMessage("int"));

    }

    @Override
    public <T> List<T> asList(final Class<T> elementClass) {
        throw new IllegalStateException(makeAsErrorMessage("List"));

    }

    @Override
    public long asLong() {
        throw new IllegalStateException(makeAsErrorMessage("long"));

    }

    @Override
    public Number asNumber() {
        throw new IllegalStateException(makeAsErrorMessage("Number"));

    }

    @Override
    public <K, V> Map<K, V> asMap(final Class<K> keyClass, final Class<V> valueClass) {
        throw new IllegalStateException(makeAsErrorMessage("Map"));
    }

    @Override
    public NdArray<?> asNdArray() {
        throw new IllegalStateException(makeAsErrorMessage("NdArray"));
    }

    @Override
    public short asShort() {
        throw new IllegalStateException(makeAsErrorMessage("short"));
    }

    @Override
    public String asString() {
        throw new IllegalStateException(makeAsErrorMessage("short"));
    }

    @Override
    public Iterator<AsdfNode> iterator() {
        return Collections.emptyIterator();
    }

    protected String makeGetErrorMessage(final String keyType) {
        return String.format("%s node does support get methods with %s key", getNodeType(), keyType);
    }

    protected String makeAsErrorMessage(final String asType) {
        return String.format("%s node cannot be represented as %s", getNodeType(), asType);
    }
}
