package cn.lsmya.smart.ktx

public fun CharSequence?.isNotNullOrEmpty(): Boolean {
    return !this.isNullOrEmpty()
}

public fun CharSequence?.replaceAll(oldValue: String, newValue: String): String {
    return if (this == null) {
        ""
    } else {
        var string = this.toString()
        while (string.contains(oldValue)) {
            string = string.replace(oldValue, newValue)
        }
        string
    }
}

@SinceKotlin("1.3")
@Suppress("BOUNDS_NOT_ALLOWED_IF_BOUNDED_BY_TYPE_PARAMETER")
public inline fun <C, R> C?.ifNullOrEmpty(defaultValue: () -> R): R where C : CharSequence, C : R =
    if (isNullOrEmpty()) defaultValue() else this