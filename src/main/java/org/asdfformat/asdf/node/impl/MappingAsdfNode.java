package org.asdfformat.asdf.node.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.asdfformat.asdf.ndarray.NdArray;
import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.node.AsdfNodeType;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;

public class MappingAsdfNode extends AsdfNodeBase {

    private final MappingNode inner;
    private final Map<AsdfNode, AsdfNode> value;

    public MappingAsdfNode(final MappingNode inner, final Map<AsdfNode, AsdfNode> value) {
        this.inner = inner;
        this.value = value;
    }

    public Map<AsdfNode, AsdfNode> getValue() {
        return value;
    }

    @Override
    protected Node getInner() {
        return inner;
    }

    @Override
    public AsdfNodeType getNodeType() {
        return AsdfNodeType.MAPPING;
    }

    @Override
    public boolean containsKey(final String key) {
        return value.containsKey(key);
    }

    @Override
    public boolean containsKey(final long key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public boolean containsKey(final boolean key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public boolean containsKey(final AsdfNode key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public AsdfNode get(final String key) {
        final AsdfNode result = value.get(key);
        if (result == null) {
            throw new IllegalArgumentException(String.format("Key does not exist: %s", key));
        }
        return result;
    }

    @Override
    public AsdfNode get(final long key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public AsdfNode get(final boolean key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public AsdfNode get(final AsdfNode key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public BigDecimal getBigDecimal(final String key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public BigDecimal getBigDecimal(final long key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public BigDecimal getBigDecimal(final boolean key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public BigDecimal getBigDecimal(final AsdfNode key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public BigInteger getBigInteger(final String key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public BigInteger getBigInteger(final long key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public BigInteger getBigInteger(final boolean key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public BigInteger getBigInteger(final AsdfNode key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public boolean getBoolean(final String key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public boolean getBoolean(final long key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public boolean getBoolean(final boolean key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public boolean getBoolean(final AsdfNode key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public byte getByte(final String key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public byte getByte(final long key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public byte getByte(final boolean key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public byte getByte(final AsdfNode key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public double getDouble(final String key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public double getDouble(final long key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public double getDouble(final boolean key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public double getDouble(final AsdfNode key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public float getFloat(final String key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public float getFloat(final long key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public float getFloat(final boolean key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public float getFloat(final AsdfNode key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public int getInt(final String key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public int getInt(final long key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public int getInt(final boolean key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public int getInt(final AsdfNode key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public <T> List<T> getList(final String key, final Class<T> elementClass) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public <T> List<T> getList(final long key, final Class<T> elementClass) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public <T> List<T> getList(final boolean key, final Class<T> elementClass) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public <T> List<T> getList(final AsdfNode key, final Class<T> elementClass) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public long getLong(final String key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public long getLong(final long key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public long getLong(final boolean key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public long getLong(final AsdfNode key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Number getNumber(final String key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Number getNumber(final long key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Number getNumber(final boolean key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Number getNumber(final AsdfNode key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public <K, V> Map<K, V> getMap(final String key, final Class<K> keyClass, final Class<V> valueClass) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public <K, V> Map<K, V> getMap(final long key, final Class<K> keyClass, final Class<V> valueClass) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public <K, V> Map<K, V> getMap(final boolean key, final Class<K> keyClass, final Class<V> valueClass) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public <K, V> Map<K, V> getMap(final AsdfNode key, final Class<K> keyClass, final Class<V> valueClass) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public NdArray<?> getNdArray(final String key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public NdArray<?> getNdArray(final long key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public NdArray<?> getNdArray(final boolean key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public NdArray<?> getNdArray(final AsdfNode key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public short getShort(final String key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public short getShort(final long key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public short getShort(final boolean key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public short getShort(final AsdfNode key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public String getString(final String key) {
        return get(key).asString();
    }

    @Override
    public String getString(final long key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public String getString(final boolean key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public String getString(final AsdfNode key) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public <K, V> Map<K, V> asMap(final Class<K> keyClass, final Class<V> valueClass) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Iterator<AsdfNode> iterator() {
        return value.keySet().iterator();
    }
}
