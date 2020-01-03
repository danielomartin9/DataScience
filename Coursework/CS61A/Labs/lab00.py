from operator import add,sub,mul
def twenty_eighteen():
    """Come up with the most creative expression that evaluates to 2018,
    using only numbers and the +, *, and - operators.

    >>> twenty_eighteen()
    2018
    """
    return add(818, (mul(20, sub(70, 10))))
