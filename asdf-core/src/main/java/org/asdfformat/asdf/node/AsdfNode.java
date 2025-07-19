package org.asdfformat.asdf.node;

import org.asdfformat.asdf.ndarray.NdArray;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;


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
     * Is this a BOOLEAN type node?
     * @return true if BOOLEAN
     */
    boolean isBoolean();

    /**
     * Is this a MAPPING type node?
     * @return true if MAPPING
     */
    boolean isMapping();

    /**
     * Is this a NDARRAY type node?
     * @return true if NDARRAY
     */
    boolean isNdArray();

    /**
     * Is this a NULL type node?
     * @return true if NULL
     */
    boolean isNull();

    /**
     * Is this a NUMBER type node?
     * @return true if NUMBER
     */
    boolean isNumber();

    /**
     * Is this a SEQUENCE type node?
     * @return true if SEQUENCE
     */
    boolean isSequence();

    /**
     * Is this a SEQUENCE type node?
     * @return true if SEQUENCE
     */
    boolean isString();

    /**
     * Is this a TIMESTAMP type node?
     * @return true if TIMESTAMP
     */
    boolean isTimestamp();

    /**
     * Does this MAPPING node contain the specified String key?
     * @param key mapping key
     * @return true if key is present
     */
    boolean containsKey(String key);

    /**
     * Does this MAPPING node contain the specified long key?
     * @param key mapping key
     * @return true if key is present
     */
    boolean containsKey(long key);

    /**
     * Does this MAPPING node contain the specified boolean key?
     * @param key mapping key
     * @return true if key is present
     */
    boolean containsKey(boolean key);

    /**
     * Does this MAPPING node contain the specified AsdfNode key?
     * @param key mapping key
     * @return true if key is present
     */
    boolean containsKey(AsdfNode key);

    /**
     * Get the size of this MAPPING or SEQUENCE node.
     * @return size
     */
    int size();

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
     * Get a mapping value as AsdfNode, indexed by AsdfNode key.
     * @param key mapping key
     * @return value
     */
    AsdfNode get(AsdfNode key);

    /**
     * Get an optional mapping value as AsdfNode, indexed by String key.
     * @param key mapping key
     * @return value
     */
    Optional<AsdfNode> getOptional(String key);

    /**
     * Get an optional sequence value or mapping value as AsdfNode, indexed by long key.
     * @param key sequence index or mapping key
     * @return value
     */
    Optional<AsdfNode> getOptional(long key);

    /**
     * Get an optional mapping value as AsdfNode, indexed by boolean key.
     * @param key mapping key
     * @return value
     */
    Optional<AsdfNode> getOptional(boolean key);

    /**
     * Get an optional mapping value as AsdfNode, indexed by AsdfNode key.
     * @param key mapping key
     * @return value
     */
    Optional<AsdfNode> getOptional(AsdfNode key);

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
     * Get a NUMBER sequence value or mapping value as BigDecimal, indexed by AsdfNode key.
     * @param key mapping key
     * @return value
     */
    BigDecimal getBigDecimal(AsdfNode key);

    /**
     * Get a NUMBER mapping value as BigDecimal, indexed by String key.
     * @param key mapping key
     * @return value
     */
    Optional<BigDecimal> getBigDecimalOptional(String key);

    /**
     * Get a NUMBER sequence value or mapping value as BigDecimal, indexed by long key.
     * @param key sequence index or mapping key
     * @return value
     */
    Optional<BigDecimal> getBigDecimalOptional(long key);

    /**
     * Get a NUMBER mapping value as BigDecimal, indexed by boolean key.
     * @param key mapping key
     * @return value
     */
    Optional<BigDecimal> getBigDecimalOptional(boolean key);

    /**
     * Get a NUMBER sequence value or mapping value as BigDecimal, indexed by AsdfNode key.
     * @param key mapping key
     * @return value
     */
    Optional<BigDecimal> getBigDecimalOptional(AsdfNode key);

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
     * Get a NUMBER sequence value or mapping value as BigInteger, indexed by AsdfNode key.
     * @param key mapping key
     * @return value
     */
    BigInteger getBigInteger(AsdfNode key);

    /**
     * Get a BOOLEAN mapping value as boolean, indexed by String key.
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
     * Get a BOOLEAN sequence value or mapping value as boolean, indexed by AsdfNode key.
     * @param key mapping key
     * @return value
     */
    boolean getBoolean(AsdfNode key);

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
     * Get a NUMBER sequence value or mapping value as byte, indexed by AsdfNode key.
     * @param key mapping key
     * @return value
     */
    byte getByte(AsdfNode key);

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
     * Get a NUMBER sequence value or mapping value as double, indexed by AsdfNode key.
     * @param key mapping key
     * @return value
     */
    double getDouble(AsdfNode key);

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
     * Get a NUMBER sequence value or mapping value as float, indexed by AsdfNode key.
     * @param key mapping key
     * @return value
     */
    float getFloat(AsdfNode key);

    /**
     * Get a TIMESTAMP mapping value as Instant, indexed by String key.
     * @param key mapping key
     * @return value
     */
    Instant getInstant(String key);

    /**
     * Get a TIMESTAMP sequence value or mapping value as Instant, indexed by long key.
     * @param key sequence index or mapping key
     * @return value
     */
    Instant getInstant(long key);

    /**
     * Get a TIMESTAMP mapping value as Instant, indexed by boolean key.
     * @param key mapping key
     * @return value
     */
    Instant getInstant(boolean key);

    /**
     * Get a TIMESTAMP sequence value or mapping value as Instant, indexed by AsdfNode key.
     * @param key mapping key
     * @return value
     */
    Instant getInstant(AsdfNode key);

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
     * Get a NUMBER sequence value or mapping value as int, indexed by AsdfNode key.
     * @param key mapping key
     * @return value
     */
    int getInt(AsdfNode key);

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
     * Get a SEQUENCE sequence value or mapping value as List, indexed by AsdfNode key.
     * @param key sequence index or mapping key
     * @param elementClass element class
     * @return value
     * @param <T> List element
     */
    <T> List<T> getList(AsdfNode key, Class<T> elementClass);

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
     * Get a NUMBER sequence value or mapping value as long, indexed by AsdfNode key.
     * @param key mapping key
     * @return value
     */
    long getLong(AsdfNode key);

    /**
     * Get a NUMBER mapping value as a Number, indexed by String key.
     * @param key mapping key
     * @return value
     */
    Number getNumber(String key);

    /**
     * Get a NUMBER sequence value or mapping value as Number, indexed by long key.
     * @param key sequence index or mapping key
     * @return value
     */
    Number getNumber(long key);

    /**
     * Get a NUMBER mapping value as Number, indexed by boolean key.
     * @param key mapping key
     * @return value
     */
    Number getNumber(boolean key);

    /**
     * Get a NUMBER sequence value or mapping value as Number, indexed by AsdfNode key.
     * @param key mapping key
     * @return value
     */
    Number getNumber(AsdfNode key);

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
     * Get a MAPPING mapping value as Map, indexed by AsdfNode key.
     * @param key sequence index or mapping key
     * @param keyClass key class
     * @param valueClass value class
     * @return value
     * @param <K> Map key
     * @param <V> Map value
     */
    <K, V> Map<K, V> getMap(AsdfNode key, Class<K> keyClass, Class<V> valueClass);

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
     * Get a NDARRAY mapping value, indexed by AsdfNode key.
     * @param key mapping key
     * @return value
     */
    NdArray<?> getNdArray(AsdfNode key);

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
     * Get a NUMBER sequence value or mapping value as short, indexed by AsdfNode key.
     * @param key mapping key
     * @return value
     */
    short getShort(AsdfNode key);

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
     * Get a STRING sequence value or mapping value, indexed by AsdfNode key.
     * @param key mapping key
     * @return value
     */
    String getString(AsdfNode key);

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
     * Get this TIMESTAMP node's value as Instant.
     * @return value
     */
    Instant asInstant();

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
     * Get this NUMBER node's value as Number.
     * @return value
     */
    Number asNumber();

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
