package cn.lsmya.smart.extension

public inline fun <T> Iterable<T>.containsField(predicate: (T) -> Boolean): Boolean {
    return !this.filter(predicate).isNullOrEmpty()
}

public inline fun <T> Collection<T>?.isNotNullOrEmpty(): Boolean = !isNullOrEmpty()

@SinceKotlin("1.3")
@Suppress("BOUNDS_NOT_ALLOWED_IF_BOUNDED_BY_TYPE_PARAMETER")
public inline fun <C, R> C?.ifNullOrEmpty(defaultValue: () -> R): R where C : Collection<*>, C : R =
    if (isNullOrEmpty()) defaultValue() else this