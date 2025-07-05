af["arr"] = np.array(
    [
        np.finfo(np.float64).min,
        np.finfo(np.float64).max,
        np.finfo(np.float64).smallest_subnormal,
        0,
        np.nan,
        np.inf,
        -np.inf,
        3.14159265358979323846264338327950288419716939937510582097494459230781640628620899,
        -3.14159265358979323846264338327950288419716939937510582097494459230781640628620899,
    ],
    dtype=np.dtype("<d")
)
