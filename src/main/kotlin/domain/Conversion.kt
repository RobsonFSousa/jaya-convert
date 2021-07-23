package domain

import java.time.LocalDateTime

data class ConversionDTO(val conversion: Conversion?)

data class Conversion(
    val id: Long? = null,
    val user: Long,
    val from: String,
    val to: String,
    val amount: Float,
    val createdAt: LocalDateTime?)