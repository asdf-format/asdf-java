package org.asdfformat.asdf.node;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.asdfformat.asdf.ndarray.NdArray;


/**
 * A single node in an ASDF tree.  May be a scalar value, sequence,
 * mapping, or n-dimensional array.
 */
public interface AsdfNode extends Iterable<AsdfNode> {

    /**
     * Get this node's YAML tag.
     * @return tag
     */
    String getTag();

    /**
     * Get this node's type, which informs the subset
     * of methods that are relevant to this node.
     * @return node type
     */
    AsdfNodeType getNodeType();

    /**
     * Get a mapping value as AsdfNode, indexed by String key.
     * @param key mapping key
     * @return value
     */
    AsdfNode get(String key);

    /**
     * Get a sequence value or mapping value as AsdfNode, indexed by long key.
     * @param key sequence index or mapping key
     * @return value
     */
    AsdfNode get(long key);

    /**
     * Get a mapping value as AsdfNode, indexed by boolean key.
     * @param key mapping key
     * @return value
     */
    AsdfNode get(boolean key);

    /**
     * Get a NUMBER mapping value as BigDecimal, indexed by String key.
     * @param key mapping key
     * @return value
     */
    BigDecimal getBigDecimal(String key);

    /**
     * Get a NUMBER sequence value or mapping value as BigDecimal, indexed by long key.
     * @param key sequence index or mapping key
     * @return value
     */
    BigDecimal getBigDecimal(long key);

    /**
     * Get a NUMBER mapping value as BigDecimal, indexed by boolean key.
     * @param key mapping key
     * @return value
     */
    BigDecimal getBigDecimal(boolean key);

    /**
     * Get a NUMBER mapping value as BigInteger, indexed by String key.
     * @param key mapping key
     * @return value
     */
    BigInteger getBigInteger(String key);

    /**
     * Get a NUMBER sequence value or mapping value as BigInteger, indexed by long key.
     * @param key sequence index or mapping key
     * @return value
     */
    BigInteger getBigInteger(long key);

    /**
     * Get a NUMBER mapping value as BigInteger, indexed by boolean key.
     * @param key mapping key
     * @return value
     */
    BigInteger getBigInteger(boolean key);

    /**
     * Get a BOOLEAN mapping value, indexed by String key.
     * @param key mapping key
     * @return value
     */
    boolean getBoolean(String key);

    /**
     * Get a BOOLEAN sequence value or mapping value, indexed by long key.
     * @param key sequence index or mapping key
     * @return value
     */
    boolean getBoolean(long key);

    /**
     * Get a BOOLEAN mapping value, indexed by boolean key.
     * @param key mapping key
     * @return value
     */
    boolean getBoolean(boolean key);

    /**
     * Get a NUMBER mapping value as byte, indexed by String key.
     * @param key mapping key
     * @return value
     */
    byte getByte(String key);

    /**
     * Get a NUMBER sequence value or mapping value as byte, indexed by long key.
     * @param key sequence index or mapping key
     * @return value
     */
    byte getByte(long key);

    /**
     * Get a NUMBER mapping value as byte, indexed by boolean key.
     * @param key mapping key
     * @return value
     */
    byte getByte(boolean key);

    /**
     * Get a NUMBER mapping value as double, indexed by String key.
     * @param key mapping key
     * @return value
     */
    double getDouble(String key);

    /**
     * Get a NUMBER sequence value or mapping value as double, indexed by long key.
     * @param key sequence index or mapping key
     * @return value
     */
    double getDouble(long key);

    /**
     * Get a NUMBER mapping value as double, indexed by boolean key.
     * @param key mapping key
     * @return value
     */
    double getDouble(boolean key);

    /**
     * Get a NUMBER mapping value as float, indexed by String key.
     * @param key mapping key
     * @return value
     */
    float getFloat(String key);

    /**
     * Get a NUMBER sequence value or mapping value as float, indexed by long key.
     * @param key sequence index or mapping key
     * @return value
     */
    float getFloat(long key);

    /**
     * Get a NUMBER mapping value as float, indexed by boolean key.
     * @param key mapping key
     * @return value
     */
    float getFloat(boolean key);

    /**
     * Get a NUMBER mapping value as int, indexed by String key.
     * @param key mapping key
     * @return value
     */
    int getInt(String key);

    /**
     * Get a NUMBER sequence value or mapping value as int, indexed by long key.
     * @param key sequence index or mapping key
     * @return value
     */
    int getInt(long key);

    /**
     * Get a NUMBER mapping value as int, indexed by boolean key.
     * @param key mapping key
     * @return value
     */
    int getInt(boolean key);

    /**
     * Get a SEQUENCE mapping value as List, indexed by String key.
     * @param key mapping key
     * @param elementClass element class
     * @return value
     * @param <T> List element
     */
    <T> List<T> getList(String key, Class<T> elementClass);

    /**
     * Get a SEQUENCE sequence value or mapping value as List, indexed by long key.
     * @param key sequence index or mapping key
     * @param elementClass element class
     * @return value
     * @param <T> List element
     */
    <T> List<T> getList(long key, Class<T> elementClass);

    /**
     * Get a SEQUENCE mapping value as List, indexed by boolean key.
     * @param key mapping key
     * @param elementClass element class
     * @return value
     * @param <T> List element
     */
    <T> List<T> getList(boolean key, Class<T> elementClass);

    /**
     * Get a NUMBER mapping value as long, indexed by String key.
     * @param key mapping key
     * @return value
     */
    long getLong(String key);

    /**
     * Get a NUMBER sequence value or mapping value as long, indexed by long key.
     * @param key sequence index or mapping key
     * @return value
     */
    long getLong(long key);

    /**
     * Get a NUMBER mapping value as long, indexed by boolean key.
     * @param key mapping key
     * @return value
     */
    long getLong(boolean key);

    /**
     * Get a MAPPING mapping value as Map, indexed by String key.
     * @param key mapping key
     * @param keyClass key class
     * @param valueClass value class
     * @return value
     * @param <K> Map key
     * @param <V> Map value
     */
    <K, V> Map<K, V> getMap(String key, Class<K> keyClass, Class<V> valueClass);

    /**
     * Get a MAPPING sequence value or mapping value as Map, indexed by long key.
     * @param key sequence index or mapping key
     * @param keyClass key class
     * @param valueClass value class
     * @return value
     * @param <K> Map key
     * @param <V> Map value
     */
    <K, V> Map<K, V> getMap(long key, Class<K> keyClass, Class<V> valueClass);

    /**
     * Get a MAPPING mapping value as Map, indexed by String key.
     * @param key mapping key
     * @param keyClass key class
     * @param valueClass value class
     * @return value
     * @param <K> Map key
     * @param <V> Map value
     */
    <K, V> Map<K, V> getMap(boolean key, Class<K> keyClass, Class<V> valueClass);

    /**
     * Get a NDARRAY mapping value, indexed by String key.
     * @param key mapping key
     * @return value
     */
     NdArray<?> getNdArray(String key);

    /**
     * Get a NDARRAY sequence value or mapping value, indexed by long key.
     * @param key sequence index or mapping key
     * @return value
     */
    NdArray<?> getNdArray(long key);

    /**
     * Get a NDARRAY mapping value, indexed by boolean key.
     * @param key mapping key
     * @return value
     */
    NdArray<?> getNdArray(boolean key);

    /**
     * Get a NUMBER mapping value as short, indexed by String key.
     * @param key mapping key
     * @return value
     */
    short getShort(String key);

    /**
     * Get a NUMBER sequence value or mapping value as short, indexed by long key.
     * @param key sequence index or mapping key
     * @return value
     */
    short getShort(long key);

    /**
     * Get a NUMBER mapping value as short, indexed by boolean key.
     * @param key mapping key
     * @return value
     */
    short getShort(boolean key);

    /**
     * Get a STRING mapping value, indexed by String key.
     * @param key mapping key
     * @return value
     */
    String getString(String key);

    /**
     * Get a STRING sequence value or mapping value, indexed by long key.
     * @param key sequence index mapping key
     * @return value
     */
    String getString(long key);

    /**
     * Get a STRING mapping value, indexed by boolean key.
     * @param key mapping key
     * @return value
     */
    String getString(boolean key);

    /**
     * Get this NUMBER node's value as BigDecimal.
     * @return value
     */
    BigDecimal asBigDecimal();

    /**
     * Get this NUMBER node's value as BigInteger.
     * @return value
     */
    BigInteger asBigInteger();

    /**
     * Get this BOOLEAN node's value.
     * @return value
     */
    boolean asBoolean();

    /**
     * Get this NUMBER node's value as byte.
     * @return value
     */
    byte asByte();

    /**
     * Get this NUMBER node's value as double.
     * @return value
     */
    double asDouble();

    /**
     * Get this NUMBER node's value as float.
     * @return value
     */
    float asFloat();

    /**
     * Get this NUMBER node's value as int.
     * @return value
     */
    int asInt();

    /**
     * Get this SEQUENCE node's value as List.
     * @param elementClass element class
     * @return value
     * @param <T> List element
     */
    <T> List<T> asList(Class<T> elementClass);

    /**
     * Get this NUMBER node's value as long.
     * @return value
     */
    long asLong();

    /**
     * Get this MAPPING node's value as Map.
     * @param keyClass key class
     * @param valueClass value class
     * @return value
     * @param <K> Map key
     * @param <V> Map value
     */
    <K, V> Map<K, V> asMap(Class<K> keyClass, Class<V> valueClass);

    /**
     * Get this NDARRAY node's value.
     * @return value
     */
    NdArray<?> asNdArray();

    /**
     * Get this NUMBER node's value as short.
     * @return value
     */
    short asShort();

    /**
     * Get this STRING node's value.
     * @return value
     */
    String asString();
}
