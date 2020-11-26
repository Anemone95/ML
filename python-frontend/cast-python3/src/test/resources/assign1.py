#!/bin/env python3
from typing import Callable


def a(x: Callable[[int], int], y: int) -> int:
    return x(y)


p = q = a


def f(func: Callable[[Callable[[int], int], int], int]) -> int:
    return func(lambda e: e + 1, 3)


def g(func: Callable[[Callable[[int], int], int], int]) -> int:
    return func(lambda e: e - 2, 4)


f(p)
g(q)
