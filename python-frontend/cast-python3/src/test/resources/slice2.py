from typing import Callable


def foo(x: int) -> int:
    return x


def a(x: Callable[[int], int]) -> int:
    return x(1)


def b(x: Callable[[int], int]) -> int:
    return x(2)


def c(x: Callable[[int], int]) -> int:
    return x(3)


def d(x: Callable[[int], int]) -> int:
    return x(4)


L = [a, b, c, d]

print(L[3](foo))

lr = L[::1]

print(lr[0](foo))

ln = L[:2]

print(ln[1](foo))

ls = L[:3]

print(ls[2](foo))

lx = L[0:4]
lx[1:3] = [a, b, c]
