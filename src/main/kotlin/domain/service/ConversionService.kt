package domain.service

import com.google.gson.Gson
import domain.Conversion
import domain.Rate
import domain.RequestAPI
import domain.repository.ConversionRepository
import io.javalin.http.BadRequestResponse
import org.http4k.client.ApacheClient
import org.http4k.core.Method
import org.http4k.core.Request

class ConversionService(val conversionRepository: ConversionRepository) {
    fun create(conversion: Conversion): Conversion? {
        if (!hasRate(conversion.from) || !hasRate(conversion.to)) {
            throw BadRequestResponse("Invalid currency")
        }

        val requestAPI = getRequestAPI()
        conversion.rate = getRate(conversion.to, requestAPI.rates) / getRate(conversion.from, requestAPI.rates)
        conversion.toValue = round(conversion.fromValue * conversion.rate)

        return conversionRepository.create(conversion)
    }

    fun all(): List<Conversion> {
        return conversionRepository.findAll()
    }

    private fun hasRate(rate: String): Boolean {
        val rates = arrayOf("EUR", "USD", "BRL", "JPY")
        return rates.contains(rate);
    }

    fun getRequestAPI(): RequestAPI {
        val client = ApacheClient()

        val request = Request(Method.GET, "http://api.exchangeratesapi.io/v1/latest")
            .query("access_key", "815f5aec19da1282a74e6b2d0a1a8f57")
            .query("base", "EUR")

        val gson = Gson()
        val res = gson.fromJson(client(request).bodyString(), RequestAPI::class.java)
        return res
    }

    fun getRate(currency: String, rates: Rate): Double {
        when (currency) {
            "EUR" -> return rates.EUR.toDouble()
            "USD" -> return rates.USD.toDouble()
            "BRL" -> return rates.BRL.toDouble()
            "JPY" -> return rates.JPY.toDouble()
            else -> return 0.0
        }
    }

    private fun round(value: Double): Double {
        return Math.round(value * 10.00) / 10.00
    }
}