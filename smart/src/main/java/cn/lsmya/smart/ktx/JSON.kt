package cn.lsmya.smart.ktx

import com.google.gson.Gson
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException
import java.lang.reflect.Type
import java.math.BigDecimal

val moshi: Moshi = Moshi.Builder()
    .add(Null_To_Empty_String_Adapter)
    .add(Null_To_INT_0_Adapter)
    .add(Null_To_LONG_0_Adapter)
    .add(Null_To_False_Adapter)
    .add(MoshiArrayListJsonAdapter.FACTORY)
    .add(BigDecimalAdapterFactory())
    .build()

fun Any.toJsonByGson(): String = Gson().toJson(this)
fun Any.toJsonByMoshi(): String = moshi.adapter(this.javaClass).toJson(this)
fun Any.toJson(): String {
    return if (this::class.java.isAnnotationPresent(JsonClass::class.java)) {
        this.toJsonByMoshi()
    } else {
        this.toJsonByGson()
    }
}

fun <T> String.fromJson(classOfT: Class<T>): T = moshi.adapter(classOfT).fromJson(this)!!

fun <T> String.fromJson(type: Type): T = moshi.adapter<T>(type).fromJson(this)!!
fun <T> String.fromJsonList(classOfT: Class<T>): ArrayList<T> =
    getClassType(classOfT).fromJson(this)!!

private fun <T> getClassType(classOfT: Class<T>): JsonAdapter<ArrayList<T>> {
    val type: Type = Types.newParameterizedType(ArrayList::class.java, classOfT)
    return moshi.adapter(type)
}

object Null_To_Empty_String_Adapter {
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

object Null_To_False_Adapter {
    @FromJson
    fun fromJson(reader: JsonReader?): Boolean {
        if (reader == null) {
            return false
        }
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextBoolean()
        }
        reader.nextNull<Unit>()
        return false
    }

}

object Null_To_INT_0_Adapter {
    @FromJson
    fun fromJson(reader: JsonReader?): Int {
        if (reader == null) {
            return 0
        }
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextInt()
        }
        reader.nextNull<Unit>()
        return 0
    }

}

object Null_To_LONG_0_Adapter {
    @FromJson
    fun fromJson(reader: JsonReader?): Long {
        if (reader == null) {
            return 0L
        }
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextLong()
        }
        reader.nextNull<Unit>()
        return 0L
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

class BigDecimalAdapterFactory : JsonAdapter.Factory {
    override fun create(type: Type, annotations: Set<Annotation?>, moshi: Moshi): JsonAdapter<*>? {
        if (type != BigDecimal::class.java) return null

        return object : JsonAdapter<BigDecimal>() {
            override fun fromJson(reader: JsonReader): BigDecimal? {
                loge("${reader.peek() == JsonReader.Token.STRING}   ${reader.peek().name}")
                return when (reader.peek()) {
                    JsonReader.Token.NUMBER -> {
                        val number = reader.nextDouble()
                        BigDecimal.valueOf(number)
                    }

                    JsonReader.Token.STRING -> {
                        val string = reader.nextString()
                        BigDecimal(string)
                    }

                    JsonReader.Token.NULL -> {
                        null
                    }

                    else -> {
                        val intValue = reader.nextInt()
                        BigDecimal(intValue)
                    }
                }
            }

            override fun toJson(wirtter: JsonWriter, value: BigDecimal?) {
                if (value != null) {
                    wirtter.value(value)
                } else {
                    wirtter.nullValue()
                }
            }
        }.nullSafe()
    }
}