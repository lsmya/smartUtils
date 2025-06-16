package cn.lsmya.smartutils

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class TestModel(
    @Json(name = "name")
    val name: String = "",
    @Json(name = "money")
    val money: BigDecimal = BigDecimal.valueOf(999)
)