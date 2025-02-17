# Proposed API

This repo contains an interface proposal for a Java implementation of an
[ASDF](https://www.asdf-format.org/en/latest/) reader.

## Examples

The following examples reference the Roman Space Telescope's
[wfi_mosaic-1.0.0](https://github.com/spacetelescope/rad/blob/0.23.1/src/rad/resources/schemas/wfi_mosaic-1.0.0.yaml) schema, which describes the structure of L3 mosaic image files for the WFI instrument.

Here's a partial visualization of an ASDF file containing a wfi_mosaic-1.0.0 object:

```
root
└─roman (wfi_mosiac-1.0.0)
  ├─meta
  │ ├─telescope: ROMAN
  │ ├─exptype: SCIENCE
  │ ├─basic (mosaic_basic-1.0.0)
  │ │ ├─time_first_mjd: 61557.0
  │ │ ├─time_last_mjd: 61557.01890925926
  │ │ ├─instrument: WFI
  │ │ ├─visit: 1
  │ └─resample (resample-1.0.0)
  │   └─members
  │     ├─[0] r0000101001001001001_0001_wfi01_cal.asdf
  │     ├─[1] r0000101001001001001_0002_wfi01_cal.asdf
  │     ├─[2] r0000101001001001001_0003_wfi01_cal.asdf
  │     └─[3] r0000101001001001001_0004_wfi01_cal.asdf
  ├─data (2D 32-bit float array, 3564 x 3564)
  ├─err (2D 32-bit float array, 3564 x 3564)
  └─context (3D 32-bit unsigned int array, 1 x 3564 x 3564)
```

Note that all Roman ASDF files contain a top-level `roman` object, which is the object that
the schema describes.

### Open an ASDF file for reading

The [Asdf](https://github.com/asdf-format/asdf-java/blob/main/src/main/java/org/asdfformat/asdf/Asdf.java) class is the point of entry for the Java ASDF reader.  Create an `Asdf` instance and use it to open
a file:

```java
// Eventually this will include configuration options
Asdf asdf = Asdf()
Path path = Paths.get("/path/to/r00001_p_v01001001001_r274dp63x31y80_f158_coadd_i2d.asdf");

try (AsdfFile asdfFile = asdf.open(path))) {
    // Interact with the ASDF file
}
```

### Fetch a node from the tree

The open [AsdfFile](https://github.com/asdf-format/asdf-java/blob/main/src/main/java/org/asdfformat/asdf/AsdfFile.java) object providers methods for accessing the ASDF file metadata (metadata related to the ASDF
file itself such as the version of the library used to write the file) and the "tree", which is the
science metadata and data (visualized above).  Since all interesting nodes are descendants of the "roman"
node, it's useful to grab a reference to that:

```java
AsdfNode roman = asdfFile.getTree().get("roman");
```

### Explore a node

The [AsdfNode](https://github.com/asdf-format/asdf-java/blob/main/src/main/java/org/asdfformat/asdf/node/AsdfNode.java) interface is a generic wrapper for a tree node that provides methods for introspecting on
the node's type and content, fetching child nodes, etc.  We know from the schema that the roman node
is expected to be a mapping, and it will identify itself as such:

```java
assert roman.getNodeType() == AsdfNodeType.MAPPING;
```

Since it is a mapping, we can iterate over it and print the keys that are present:

```java
for (AsdfNode key : roman) {
    System.out.println(key.asString());
}
```

```
meta
data
err
context
```

We can also descend into the "meta" child node and print its keys:

```java
for (AsdfNode metaKey : roman.get("meta")) {
    System.out.println(metaKey.asString());
}
```

```
telescope
exptype
basic
resample
```

### Fetching values

In order to make use of the information in the file, we're going to need to
convert the nodes to familiar types such as String, double, int, List, etc.

For example, we know from the schema that the roman.meta.telescope node
is a string, so we can descend into the "meta" node and fetch the string
from there:

```java
String telescope = roman.get("meta").getString("telescope");
```

On the other hand, if we're uncertain of the type of the "telescope"
node, we can obtain a reference to it as an `AsdfNode` and check the
type before proceeding:

```java
AsdfNode telescopeNode = roman.get("meta").get("telescope");
if (telescopeNode.getNodeType() == AsdfNodeType.STRING) {
    String telescope = telescopeNode.asString();
}
```

Numeric values require more judgement.  Since numbers in an ASDF tree
are written as text, it's unclear without the schema what Java primitive
type is appropriate.  For example, the roman.meta.basic.time_last_mjd
node is numeric:

```java
assert roman.get("meta").get("basic").get("time_last_mjd").getNodeType() == AsdfNodeType.NUMBER;
```

but attempting to fetch its value as an int, long, or single-precision float will
result in an `ArithmeticException`:

```java
// Throws ArithmeticException
roman.get("meta").get("basic").getInt("time_last_mjd");
// Throws ArithmeticException
roman.get("meta").get("basic").getLong("time_last_mjd");
// Throws ArithmeticException
roman.get("meta").get("basic").getFloat("time_last_mjd");
```

The value can be represented as a double-precision float, so `getDouble` succeeds:

```java
// Succeeds
roman.get("meta").get("basic").getDouble("time_last_mjd");
```

`BigDecimal` is a good option when uncertain of the precision needed:

```java
// Succeeds
roman.get("meta").get("basic").getBigDecimal("time_last_mjd");
```

### Working with sequences

Sequence elements can also be indexed directly from `AsdfNode`:

```java
String filename = roman.get("meta").get("resample").get("members").getString(2);
```

Sequences with consistent value types can be converted to `List`:

```java
List<String> filenames = roman.get("meta").get("resample").getList("members", String.class);
```

When iterated, sequence nodes yield their element nodes:

```java
for (AsdfNode element : roman.get("meta").get("resample").get("members")) {
    System.out.println(element.asString());
}
```

```
r0000101001001001001_0001_wfi01_cal.asdf
r0000101001001001001_0002_wfi01_cal.asdf
r0000101001001001001_0003_wfi01_cal.asdf
r0000101001001001001_0004_wfi01_cal.asdf
```

### Working with n-dimensional arrays

N-dimensional arrays such as the roman.data array are accessed through the [NdArray](https://github.com/asdf-format/asdf-java/blob/main/src/main/java/org/asdfformat/asdf/ndarray/NdArray.java) interface:

```java
assert roman.get("data").getNodeType() == AsdfNodeType.NDARRAY;

NdArray<?> data = roman.getNdArray("data");
```

Unlike numeric values in the tree, ASDF arrays have well-defined datatypes:

```java
assert data.getDataType() == DataType.FLOAT32;
```

To access array elements, first convert the array to a view specific to a Java primitive type:

```java
FloatNdArray floatData = data.asFloatNdArray();

// Get a single element
float value = floatData.get(100, 100);

// Get the entire array as a Java primitive array:
float[][] array = floatData.toArray(new float[0][0]);
```

#### Array shape

The shape of an array is described by the [Shape](https://github.com/asdf-format/asdf-java/blob/main/src/main/java/org/asdfformat/asdf/ndarray/Shape.java) interface, returned by `NdArray.getShape`:

```java
Shape shape = roman.getNdArray("context").getShape();

for (int dimension = 0; dimension < shape.getRank(); dimension++) {
    System.out.println(String.format("[%d]: %d", dimension, shape.get(dimension)));
}
```

```
[0]: 1
[1]: 3564
[2]: 3564
```

#### Slicing arrays

The `NdArray` class offers basic support for selecting portions of arrays.  The `NdArray.index` method
selects one or more indices, returning a view of the array with reduced rank:

```java
FloatNdArray data = roman.getNdArray("data").asFloatNdArray();
assert data.getShape().getRank() == 2;

FloatNdArray dataAtIndexZero = data.index(0);
assert dataAtIndexZero.getShape().getRank() == 1;

float[] arrayAtIndexZero = dataAtIndexZero.toArray(new float[0]);
```

The `NdArray.slice` method allows us to select ranges over one or more dimensions:

```java
FloatNdArray slicedData = data.slice(Slices.range(0, 100), Slices.range(200, 400));
assert slicedData.getShape().getRank() == 2;
assert slicedData.getShape().get(0) == 100;
assert slicedData.getShape().get(1) == 200;

// Showing the relationship with the original array:
assert slicedData.get(0, 0) == data.get(0, 200);

float[][] slicedArray = slicedData.toArray(new float[0][0]);
```

#### Note on unsigned datatypes

Since Java does not offer unsigned integral types, the user must choose how
they want to work ASDF unsigned datatypes such as UINT32, UINT64, etc.

For some purposes it is acceptable to read the array data into the equivalent
width signed Java type:

```java
assert roman.getNdArray("context").getDataType() == DataType.UINT32;

IntNdArray context = roman.getNdArray("context").asIntNdArray();

int[][][] contextArray = context.toArray(new int[0][0][0]);
```

The library will not throw an exception, but the user should be aware that
large unsigned values will be treated as negative numbers.

The alternative is to read the data into a wider Java type that
can represent the full range of unsigned values of the original,
smaller type:

```java
LongNdArray context = roman.getNdArray("context").asLongNdArray();

long[][][] contextArray = context.toArray(new long[0][0][0]);
```

Similarly, UINT64 values can be read into `BigInteger`, though this
may be slow.

#### Note on byte order

The ASDF format is capable of storing array data in little-endian or big-endian
byte order.  Since Java's native byte order is big-endian, any arrays stored
in little-endian are automatically converted before any array data is returned
to the user.

#### Note on strides

The ASDF format is capable of storing array data in row-major or column-major
order, as well as more exotic stride configurations.  Java, on the other hand,
does not support multi-dimensional primitive arrays stored in contiguous memory.
For example, this array:

```java
byte[][] data = new byte[5][4];
```

is not stored in a single 20-byte block of memory, but instead is
an array of pointers to 5 distinct 4-byte blocks of memory.

When this library returns Java primitive arrays, they will be allocated in
this way regardless of the underlying storage configuration of the ASDF file.

Array indexing is always row-first.

#### Note on compression

The ASDF format also is capable of storing array data in a compressed state,
using one of a handful of compression algorithms.  To improve the efficiency
of repeated access to compressed arrays, this library will decompress each
array to a temporary file on first access.

Compression features will be configurable upon creation of the `Asdf` instance.
