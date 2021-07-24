package domain

import java.time.LocalDateTime

data class ConversionDTO(val conversion: Conversion?)

data class Conversion(
    val id: Long? = null,
    val user: Long,
    val from: String,
    val fromValue: Double = 0.0,
    val to: String,
    var toValue: Double = 0.0,
    var rate: Double = 0.0,
    val createdAt: LocalDateTime?)