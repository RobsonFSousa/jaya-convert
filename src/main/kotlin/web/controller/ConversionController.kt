package web.controller

import domain.ConversionDTO
import domain.ConversionsDTO
import domain.service.ConversionService
import io.javalin.http.Context
import org.eclipse.jetty.http.HttpStatus


class ConversionController(private val conversionService: ConversionService) {

    fun create(ctx: Context) {
        ctx.bodyValidator<ConversionDTO>()
            .check({ it.conversion != null })
            .check({ it.conversion?.user?.compareTo(0) ?: 1 > 0 })
            .check({ !it.conversion?.from.isNullOrBlank() })
            .check({ !it.conversion?.to.isNullOrBlank() })
            .check({ it.conversion?.fromValue?.compareTo(0.0) ?: 1 > 0 })
            .get().conversion?.also { conversion ->
                conversionService.create(conversion).apply {
                    ctx.json(ConversionDTO(this))
                    ctx.status(HttpStatus.CREATED_201)
                }
            }
    }

    fun findByUser(ctx: Context) {
        ctx.pathParam<String>("user").get().also { user ->
            conversionService.findByUser(user.toLong()).apply {
                ctx.json(ConversionsDTO(this))
            }
        }
    }
}