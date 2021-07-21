package controller

import io.javalin.http.Context
import service.ConversionService

object ConversionController {
    fun convert(ctx: Context) {
        //TODO
    }

    fun listAll(ctx: Context) {
        val conversionService = ConversionService()
        val conversions = conversionService.listAll()
        ctx.json(conversions)
    }
}