package controller

import domain.ConversionDTO
import domain.service.ConversionService
import io.javalin.http.Context


class ConversionController(private val conversionService: ConversionService) {

    fun create(ctx: Context) {
        ctx.bodyValidator<ConversionDTO>()
            .check({ it.conversion != null })
            .check({ !it.conversion?.from.isNullOrBlank() })
            .check({ !it.conversion?.to.isNullOrBlank() })
            .check({ it.conversion?.amount?.compareTo(0.0) ?: 1 > 0 })
            .get().conversion?.also { conversion ->
                conversionService.create(conversion).apply {
                    ctx.json(ConversionDTO(this))
                }
            }
    }

    fun all(ctx: Context) {
        val conversions = conversionService.all()
        ctx.json(conversions)
    }
}