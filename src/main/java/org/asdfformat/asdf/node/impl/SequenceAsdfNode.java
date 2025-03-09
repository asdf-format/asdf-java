package org.asdfformat.asdf.node.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.asdfformat.asdf.ndarray.NdArray;
import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.node.AsdfNodeType;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.SequenceNode;

public class SequenceAsdfNode extends AsdfNodeBase {

    private final SequenceNode inner;

    public SequenceAsdfNode(final SequenceNode inner) {
        this.inner = inner;
    }

    @Override
    public AsdfNodeType getNodeType() {
        return AsdfNodeType.SEQUENCE;
    }

    @Override
    protected Node getInner() {
        return inner;
    }

    @Override
    public AsdfNode get(final long key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public AsdfNode get(final boolean key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with boolean key");
    }

    @Override
    public AsdfNode get(final AsdfNode key) {
        throw new RuntimeException("Not yet implemented");    }

    @Override
    public BigDecimal getBigDecimal(final String key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with String key");
    }

    @Override
    public BigDecimal getBigDecimal(final long key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public BigDecimal getBigDecimal(final boolean key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with boolean key");
    }

    @Override
    public BigDecimal getBigDecimal(final AsdfNode key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public BigInteger getBigInteger(final String key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with String key");
    }

    @Override
    public BigInteger getBigInteger(final long key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public BigInteger getBigInteger(final boolean key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with boolean key");
    }

    @Override
    public BigInteger getBigInteger(final AsdfNode key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public boolean getBoolean(final String key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with String key");
    }

    @Override
    public boolean getBoolean(final long key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public boolean getBoolean(final boolean key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with boolean key");
    }

    @Override
    public boolean getBoolean(final AsdfNode key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public byte getByte(final String key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with String key");
    }

    @Override
    public byte getByte(final long key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public byte getByte(final boolean key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with boolean key");
    }

    @Override
    public byte getByte(final AsdfNode key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public double getDouble(final String key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with String key");
    }

    @Override
    public double getDouble(final long key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public double getDouble(final boolean key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with boolean key");
    }

    @Override
    public double getDouble(final AsdfNode key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public float getFloat(final String key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with String key");
    }

    @Override
    public float getFloat(final long key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public float getFloat(final boolean key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with boolean key");
    }

    @Override
    public float getFloat(final AsdfNode key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public int getInt(final String key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with String key");
    }

    @Override
    public int getInt(final long key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public int getInt(final boolean key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with boolean key");
    }

    @Override
    public int getInt(final AsdfNode key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public <T> List<T> getList(final String key, final Class<T> elementClass) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with String key");
    }

    @Override
    public <T> List<T> getList(final long key, final Class<T> elementClass) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public <T> List<T> getList(final boolean key, final Class<T> elementClass) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with boolean key");
    }

    @Override
    public <T> List<T> getList(final AsdfNode key, final Class<T> elementClass) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public long getLong(final String key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with String key");
    }

    @Override
    public long getLong(final long key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public long getLong(final boolean key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with boolean key");
    }

    @Override
    public long getLong(final AsdfNode key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public Number getNumber(final String key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with String key");
    }

    @Override
    public Number getNumber(final long key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public Number getNumber(final boolean key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods with boolean key");
    }

    @Override
    public Number getNumber(final AsdfNode key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public <K, V> Map<K, V> getMap(final String key, final Class<K> keyClass, final Class<V> valueClass) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods");
    }

    @Override
    public <K, V> Map<K, V> getMap(final long key, final Class<K> keyClass, final Class<V> valueClass) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods");
    }

    @Override
    public <K, V> Map<K, V> getMap(final boolean key, final Class<K> keyClass, final Class<V> valueClass) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods");
    }

    @Override
    public NdArray<?> getNdArray(final String key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods");
    }

    @Override
    public NdArray<?> getNdArray(final long key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods");
    }

    @Override
    public NdArray<?> getNdArray(final boolean key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods");
    }

    @Override
    public short getShort(final String key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods");
    }

    @Override
    public short getShort(final long key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods");
    }

    @Override
    public short getShort(final boolean key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods");
    }

    @Override
    public short getShort(final AsdfNode key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods");
    }

    @Override
    public String getString(final String key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods");
    }

    @Override
    public String getString(final long key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods");
    }

    @Override
    public String getString(final boolean key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods");
    }

    @Override
    public String getString(final AsdfNode key) {
        throw new IllegalStateException("SEQUENCE nodes do not support get methods");
    }

    @Override
    public BigDecimal asBigDecimal() {
        throw new IllegalStateException("SEQUENCE nodes cannot be represented as BigDecimal");
    }

    @Override
    public BigInteger asBigInteger() {
        throw new IllegalStateException("SEQUENCE nodes cannot be represented as BigInteger");
    }

    @Override
    public boolean asBoolean() {
        throw new IllegalStateException("SEQUENCE nodes cannot be represented as boolean");
    }

    @Override
    public byte asByte() {
        throw new IllegalStateException("SEQUENCE nodes cannot be represented as byte");
    }

    @Override
    public double asDouble() {
        throw new IllegalStateException("SEQUENCE nodes cannot be represented as double");
    }

    @Override
    public float asFloat() {
        throw new IllegalStateException("SEQUENCE nodes cannot be represented as float");
    }

    @Override
    public int asInt() {
        throw new IllegalStateException("SEQUENCE nodes cannot be represented as int");
    }

    @Override
    public <T> List<T> asList(final Class<T> elementClass) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public long asLong() {
        throw new IllegalStateException("SEQUENCE nodes cannot be represented as long");
    }

    @Override
    public Number asNumber() {
        throw new IllegalStateException("SEQUENCE nodes cannot be represented as Number");
    }

    @Override
    public <K, V> Map<K, V> asMap(final Class<K> keyClass, final Class<V> valueClass) {
        throw new IllegalStateException("SEQUENCE nodes cannot be represented as Map");
    }

    @Override
    public NdArray<?> asNdArray() {
        throw new IllegalStateException("SEQUENCE nodes cannot be represented as NdArray");
    }

    @Override
    public short asShort() {
        throw new IllegalStateException("SEQUENCE nodes cannot be represented as short");
    }

    @Override
    public String asString() {
        throw new IllegalStateException("SEQUENCE nodes cannot be represented as String");
    }

    @Override
    public Iterator<AsdfNode> iterator() {
        throw new RuntimeException("Not yet implemented");
    }
}
