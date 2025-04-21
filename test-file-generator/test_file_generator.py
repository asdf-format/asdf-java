import argparse
import sys
from io import BytesIO

import asdf
import numpy as np


def parse_args():
    parser = argparse.ArgumentParser()

    parser.add_argument("--version", required=True)

    return parser.parse_args()


def main():
    args = parse_args()

    af = asdf.AsdfFile(version=args.version)

    script = sys.stdin.read()
    exec(script, {}, {"af": af, "np": np})

    buffer = BytesIO()
    af.write_to(buffer)

    buffer.seek(0)
    sys.stdout.buffer.write(buffer.read())


if __name__ == "__main__":
    main();
