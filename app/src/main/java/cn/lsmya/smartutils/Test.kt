package cn.lsmya.smartutils

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Test(
    @Json(name = "id")
    val id: String = "",
    @Json(name = "name")
    val name: String = "",
    @Json(name = "url")
    val url: String = ""
)