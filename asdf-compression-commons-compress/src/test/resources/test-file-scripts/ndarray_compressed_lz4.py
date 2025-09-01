af["arr"] = np.arange(0, 10, dtype=np.float64)

af.set_array_compression(af["arr"], "lz4")
