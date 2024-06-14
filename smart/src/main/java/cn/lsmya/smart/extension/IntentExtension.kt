package cn.lsmya.smart.extension

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> Activity.intentExtras(name: String): ActivityExtras<T?> = ActivityExtras(name, null)

fun <T> Activity.intentExtras(name: String, defaultValue: T) = ActivityExtras(name, defaultValue)

fun <T> Fragment.intentExtras(name: String): FragmentExtras<T?> = FragmentExtras(name, null)

fun <T> Fragment.intentExtras(name: String, defaultValue: T) = FragmentExtras(name, defaultValue)


class ActivityExtras<T>(private val name: String, private val defaultValue: T) :
    ReadWriteProperty<Activity, T> {

    private var extra: T? = null

    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        return extra
            ?: thisRef.intent?.extras?.take<T>(name)?.also { extra = it }
            ?: defaultValue.also { extra = it }
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: T) {
        extra = value
    }
}


class FragmentExtras<T>(private val name: String, private val defaultValue: T) :
    ReadWriteProperty<Fragment, T> {

    private var extra: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return extra
            ?: thisRef.arguments?.take<T>(name)?.also { extra = it }
            ?: defaultValue.also { extra = it }
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        extra = value
    }
}


@Suppress("UNCHECKED_CAST")
fun <T> Bundle.take(key: String): T? {
    try {
        return get(key) as? T?
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}