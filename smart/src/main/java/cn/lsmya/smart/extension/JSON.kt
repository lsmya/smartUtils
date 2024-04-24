package cn.lsmya.smart.extension

import com.google.gson.Gson
import com.squareup.moshi.*
import com.squareup.moshi.JsonAdapter.Factory
import java.io.IOException
import java.lang.reflect.Type

private val moshi: Moshi by lazy {
    Moshi.Builder()
        .add(NullToEmptyStringAdapter)
        .add(MoshiArrayListJsonAdapter.FACTORY)
        .build()
}

fun Any.toJson(): String = Gson().toJson(this)

fun <T> String.fromJson(classOfT: Class<T>): T = moshi.adapter(classOfT).fromJson(this)!!

fun <T> String.fromJson(type: Type): T = moshi.adapter<T>(type).fromJson(this)!!

fun <T> String.fromJsonList(classOfT: Class<T>): ArrayList<T> =
    this.fromJson(Types.newParameterizedType(ArrayList::class.java, classOfT))

private object NullToEmptyStringAdapter {
    @FromJson
    fun fromJson(reader: JsonReader?): String {
        if (reader == null) {
            return ""
        }
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }

}

private abstract class MoshiArrayListJsonAdapter<C : MutableCollection<T>?, T> private constructor(
    private val elementAdapter: JsonAdapter<T>
) :
    JsonAdapter<C>() {
    abstract fun newCollection(): C

    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): C {
        val result = newCollection()
        reader.beginArray()
        while (reader.hasNext()) {
            result?.add(elementAdapter.fromJson(reader)!!)
        }
        reader.endArray()
        return result
    }

    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: C?) {
        writer.beginArray()
        for (element in value!!) {
            elementAdapter.toJson(writer, element)
        }
        writer.endArray()
    }

    override fun toString(): String {
        return "$elementAdapter.collection()"
    }

    companion object {
        val FACTORY = Factory { type, annotations, moshi ->
            val rawType = Types.getRawType(type)
            if (annotations.isNotEmpty()) return@Factory null
            if (rawType == ArrayList::class.java) {
                return@Factory newArrayListAdapter<Any>(
                    type,
                    moshi
                ).nullSafe()
            }
            null
        }

        private fun <T> newArrayListAdapter(
            type: Type,
            moshi: Moshi
        ): JsonAdapter<MutableCollection<T>> {
            val elementType =
                Types.collectionElementType(
                    type,
                    MutableCollection::class.java
                )

            val elementAdapter: JsonAdapter<T> = moshi.adapter(elementType)

            return object :
                MoshiArrayListJsonAdapter<MutableCollection<T>, T>(elementAdapter) {
                override fun newCollection(): MutableCollection<T> {
                    return ArrayList()
                }
            }
        }
    }
}

