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

public class AsdfNodeImpl implements AsdfNode {
    private final Node inner;

    public AsdfNodeImpl(final Node inner) {
        this.inner = inner;
    }

    @Override
    public String getTag() {
        return inner.getTag().getValue();
    }

    @Override
    public AsdfNodeType getNodeType() {
        return null;
    }

    @Override
    public AsdfNode get(final String key) {
        return null;
    }

    @Override
    public AsdfNode get(final long key) {
        return null;
    }

    @Override
    public AsdfNode get(final boolean key) {
        return null;
    }

    @Override
    public BigDecimal getBigDecimal(final String key) {
        return null;
    }

    @Override
    public BigDecimal getBigDecimal(final long key) {
        return null;
    }

    @Override
    public BigDecimal getBigDecimal(final boolean key) {
        return null;
    }

    @Override
    public BigInteger getBigInteger(final String key) {
        return null;
    }

    @Override
    public BigInteger getBigInteger(final long key) {
        return null;
    }

    @Override
    public BigInteger getBigInteger(final boolean key) {
        return null;
    }

    @Override
    public boolean getBoolean(final String key) {
        return false;
    }

    @Override
    public boolean getBoolean(final long key) {
        return false;
    }

    @Override
    public boolean getBoolean(final boolean key) {
        return false;
    }

    @Override
    public byte getByte(final String key) {
        return 0;
    }

    @Override
    public byte getByte(final long key) {
        return 0;
    }

    @Override
    public byte getByte(final boolean key) {
        return 0;
    }

    @Override
    public double getDouble(final String key) {
        return 0;
    }

    @Override
    public double getDouble(final long key) {
        return 0;
    }

    @Override
    public double getDouble(final boolean key) {
        return 0;
    }

    @Override
    public float getFloat(final String key) {
        return 0;
    }

    @Override
    public float getFloat(final long key) {
        return 0;
    }

    @Override
    public float getFloat(final boolean key) {
        return 0;
    }

    @Override
    public int getInt(final String key) {
        return 0;
    }

    @Override
    public int getInt(final long key) {
        return 0;
    }

    @Override
    public int getInt(final boolean key) {
        return 0;
    }

    @Override
    public <T> List<T> getList(final String key, final Class<T> elementClass) {
        return Collections.emptyList();
    }

    @Override
    public <T> List<T> getList(final long key, final Class<T> elementClass) {
        return Collections.emptyList();
    }

    @Override
    public <T> List<T> getList(final boolean key, final Class<T> elementClass) {
        return Collections.emptyList();
    }

    @Override
    public long getLong(final String key) {
        return 0;
    }

    @Override
    public long getLong(final long key) {
        return 0;
    }

    @Override
    public long getLong(final boolean key) {
        return 0;
    }

    @Override
    public <K, V> Map<K, V> getMap(final String key, final Class<K> keyClass, final Class<V> valueClass) {
        return Collections.emptyMap();
    }

    @Override
    public <K, V> Map<K, V> getMap(final long key, final Class<K> keyClass, final Class<V> valueClass) {
        return Collections.emptyMap();
    }

    @Override
    public <K, V> Map<K, V> getMap(final boolean key, final Class<K> keyClass, final Class<V> valueClass) {
        return Collections.emptyMap();
    }

    @Override
    public NdArray<?> getNdArray(final String key) {
        return null;
    }

    @Override
    public NdArray<?> getNdArray(final long key) {
        return null;
    }

    @Override
    public NdArray<?> getNdArray(final boolean key) {
        return null;
    }

    @Override
    public short getShort(final String key) {
        return 0;
    }

    @Override
    public short getShort(final long key) {
        return 0;
    }

    @Override
    public short getShort(final boolean key) {
        return 0;
    }

    @Override
    public String getString(final String key) {
        return "";
    }

    @Override
    public String getString(final long key) {
        return "";
    }

    @Override
    public String getString(final boolean key) {
        return "";
    }

    @Override
    public BigDecimal asBigDecimal() {
        return null;
    }

    @Override
    public BigInteger asBigInteger() {
        return null;
    }

    @Override
    public boolean asBoolean() {
        return false;
    }

    @Override
    public byte asByte() {
        return 0;
    }

    @Override
    public double asDouble() {
        return 0;
    }

    @Override
    public float asFloat() {
        return 0;
    }

    @Override
    public int asInt() {
        return 0;
    }

    @Override
    public <T> List<T> asList(final Class<T> elementClass) {
        return Collections.emptyList();
    }

    @Override
    public long asLong() {
        return 0;
    }

    @Override
    public <K, V> Map<K, V> asMap(final Class<K> keyClass, final Class<V> valueClass) {
        return Collections.emptyMap();
    }

    @Override
    public NdArray<?> asNdArray() {
        return null;
    }

    @Override
    public short asShort() {
        return 0;
    }

    @Override
    public String asString() {
        return "";
    }

    @Override
    public Iterator<AsdfNode> iterator() {
        return null;
    }
}
