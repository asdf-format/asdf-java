af["arr"] = np.array(
    [
        -np.finfo(np.float16).max,
        np.finfo(np.float16).max,
        np.finfo(np.float16).smallest_subnormal,
        0,
        np.nan,
        np.inf,
        -np.inf,
        np.float16(3.14),
        np.float16(-3.14),
    ],
    dtype=np.float16
)

af.set_array_storage(af["arr"], "inline")
