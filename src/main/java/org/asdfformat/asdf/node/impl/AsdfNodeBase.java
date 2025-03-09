package org.asdfformat.asdf.node.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.asdfformat.asdf.ndarray.NdArray;
import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.node.AsdfNodeType;
import org.yaml.snakeyaml.nodes.Node;

public abstract class AsdfNodeBase implements AsdfNode {

    protected abstract Node getInner();

    @Override
    public String getTag() {
        return getInner().getTag().getValue();
    }

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
    public boolean containsKey(final String key) {
        return false;
    }

    @Override
    public boolean containsKey(final long key) {
        return false;
    }

    @Override
    public boolean containsKey(final boolean key) {
        return false;
    }

    @Override
    public boolean containsKey(final AsdfNode key) {
        return false;
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
    public BigDecimal getBigDecimal(final String key) {
        throw new IllegalStateException(makeGetErrorMessage("String"));
    }

    @Override
    public BigDecimal getBigDecimal(final long key) {
        throw new IllegalStateException(makeGetErrorMessage("long"));
    }

    @Override
    public BigDecimal getBigDecimal(final boolean key) {
        throw new IllegalStateException(makeGetErrorMessage("boolean"));
    }

    @Override
    public BigDecimal getBigDecimal(final AsdfNode key) {
        throw new IllegalStateException(makeGetErrorMessage("AsdfNode"));
    }

    @Override
    public BigInteger getBigInteger(final String key) {
        throw new IllegalStateException(makeGetErrorMessage("String"));
    }

    @Override
    public BigInteger getBigInteger(final long key) {
        throw new IllegalStateException(makeGetErrorMessage("long"));
    }

    @Override
    public BigInteger getBigInteger(final boolean key) {
        throw new IllegalStateException(makeGetErrorMessage("boolean"));
    }

    @Override
    public BigInteger getBigInteger(final AsdfNode key) {
        throw new IllegalStateException(makeGetErrorMessage("AsdfNode"));
    }

    @Override
    public boolean getBoolean(final String key) {
        throw new IllegalStateException(makeGetErrorMessage("String"));
    }

    @Override
    public boolean getBoolean(final long key) {
        throw new IllegalStateException(makeGetErrorMessage("long"));
    }

    @Override
    public boolean getBoolean(final boolean key) {
        throw new IllegalStateException(makeGetErrorMessage("boolean"));
    }

    @Override
    public boolean getBoolean(final AsdfNode key) {
        throw new IllegalStateException(makeGetErrorMessage("AsdfNode"));
    }

    @Override
    public byte getByte(final String key) {
        throw new IllegalStateException(makeGetErrorMessage("String"));
    }

    @Override
    public byte getByte(final long key) {
        throw new IllegalStateException(makeGetErrorMessage("long"));
    }

    @Override
    public byte getByte(final boolean key) {
        throw new IllegalStateException(makeGetErrorMessage("boolean"));
    }

    @Override
    public byte getByte(final AsdfNode key) {
        throw new IllegalStateException(makeGetErrorMessage("AsdfNode"));
    }

    @Override
    public double getDouble(final String key) {
        throw new IllegalStateException(makeGetErrorMessage("String"));
    }

    @Override
    public double getDouble(final long key) {
        throw new IllegalStateException(makeGetErrorMessage("long"));
    }

    @Override
    public double getDouble(final boolean key) {
        throw new IllegalStateException(makeGetErrorMessage("boolean"));
    }

    @Override
    public double getDouble(final AsdfNode key) {
        throw new IllegalStateException(makeGetErrorMessage("AsdfNode"));
    }

    @Override
    public float getFloat(final String key) {
        throw new IllegalStateException(makeGetErrorMessage("String"));
    }

    @Override
    public float getFloat(final long key) {
        throw new IllegalStateException(makeGetErrorMessage("long"));
    }

    @Override
    public float getFloat(final boolean key) {
        throw new IllegalStateException(makeGetErrorMessage("boolean"));
    }

    @Override
    public float getFloat(final AsdfNode key) {
        throw new IllegalStateException(makeGetErrorMessage("AsdfNode"));
    }

    @Override
    public int getInt(final String key) {
        throw new IllegalStateException(makeGetErrorMessage("String"));
    }

    @Override
    public int getInt(final long key) {
        throw new IllegalStateException(makeGetErrorMessage("long"));
    }

    @Override
    public int getInt(final boolean key) {
        throw new IllegalStateException(makeGetErrorMessage("boolean"));
    }

    @Override
    public int getInt(final AsdfNode key) {
        throw new IllegalStateException(makeGetErrorMessage("AsdfNode"));
    }

    @Override
    public <T> List<T> getList(final String key, final Class<T> elementClass) {
        throw new IllegalStateException(makeGetErrorMessage("String"));
    }

    @Override
    public <T> List<T> getList(final long key, final Class<T> elementClass) {
        throw new IllegalStateException(makeGetErrorMessage("long"));
    }

    @Override
    public <T> List<T> getList(final boolean key, final Class<T> elementClass) {
        throw new IllegalStateException(makeGetErrorMessage("boolean"));
    }

    @Override
    public <T> List<T> getList(final AsdfNode key, final Class<T> elementClass) {
        throw new IllegalStateException(makeGetErrorMessage("AsdfNode"));
    }

    @Override
    public long getLong(final String key) {
        throw new IllegalStateException(makeGetErrorMessage("String"));
    }

    @Override
    public long getLong(final long key) {
        throw new IllegalStateException(makeGetErrorMessage("long"));
    }

    @Override
    public long getLong(final boolean key) {
        throw new IllegalStateException(makeGetErrorMessage("boolean"));
    }

    @Override
    public long getLong(final AsdfNode key) {
        throw new IllegalStateException(makeGetErrorMessage("AsdfNode"));
    }

    @Override
    public Number getNumber(final String key) {
        throw new IllegalStateException(makeGetErrorMessage("String"));
    }

    @Override
    public Number getNumber(final long key) {
        throw new IllegalStateException(makeGetErrorMessage("long"));
    }

    @Override
    public Number getNumber(final boolean key) {
        throw new IllegalStateException(makeGetErrorMessage("boolean"));
    }

    @Override
    public Number getNumber(final AsdfNode key) {
        throw new IllegalStateException(makeGetErrorMessage("AsdfNode"));
    }

    @Override
    public <K, V> Map<K, V> getMap(final String key, final Class<K> keyClass, final Class<V> valueClass) {
        throw new IllegalStateException(makeGetErrorMessage("String"));
    }

    @Override
    public <K, V> Map<K, V> getMap(final long key, final Class<K> keyClass, final Class<V> valueClass) {
        throw new IllegalStateException(makeGetErrorMessage("long"));
    }

    @Override
    public <K, V> Map<K, V> getMap(final boolean key, final Class<K> keyClass, final Class<V> valueClass) {
        throw new IllegalStateException(makeGetErrorMessage("boolean"));
    }

    @Override
    public <K, V> Map<K, V> getMap(final AsdfNode key, final Class<K> keyClass, final Class<V> valueClass) {
        throw new IllegalStateException(makeGetErrorMessage("AsdfNode"));
    }

    @Override
    public NdArray<?> getNdArray(final String key) {
        throw new IllegalStateException(makeGetErrorMessage("String"));
    }

    @Override
    public NdArray<?> getNdArray(final long key) {
        throw new IllegalStateException(makeGetErrorMessage("long"));
    }

    @Override
    public NdArray<?> getNdArray(final boolean key) {
        throw new IllegalStateException(makeGetErrorMessage("boolean"));
    }

    @Override
    public NdArray<?> getNdArray(final AsdfNode key) {
        throw new IllegalStateException(makeGetErrorMessage("AsdfNode"));
    }

    @Override
    public short getShort(final String key) {
        throw new IllegalStateException(makeGetErrorMessage("String"));
    }

    @Override
    public short getShort(final long key) {
        throw new IllegalStateException(makeGetErrorMessage("long"));
    }

    @Override
    public short getShort(final boolean key) {
        throw new IllegalStateException(makeGetErrorMessage("boolean"));
    }

    @Override
    public short getShort(final AsdfNode key) {
        throw new IllegalStateException(makeGetErrorMessage("AsdfNode"));
    }

    @Override
    public String getString(final String key) {
        throw new IllegalStateException(makeGetErrorMessage("String"));
    }

    @Override
    public String getString(final long key) {
        throw new IllegalStateException(makeGetErrorMessage("long"));
    }

    @Override
    public String getString(final boolean key) {
        throw new IllegalStateException(makeGetErrorMessage("boolean"));
    }

    @Override
    public String getString(final AsdfNode key) {
        throw new IllegalStateException(makeGetErrorMessage("AsdfNode"));
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
        throw new IllegalStateException(makeIteratorErrorMessage());
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

    protected String makeIteratorErrorMessage() {
        return String.format("%s node cannot be iterated", getNodeType());
    }
}
