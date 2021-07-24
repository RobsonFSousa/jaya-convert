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

class ConversionService(private val conversionRepository: ConversionRepository) {
    fun create(conversion: Conversion): Conversion? {
        if (!hasRate(conversion.from) || !hasRate(conversion.to)) {
            throw BadRequestResponse("Invalid currency")
        }

        val requestAPI = getRequestAPI()
        conversion.rate = getRate(conversion.to, requestAPI.rates) / getRate(conversion.from, requestAPI.rates)

        val newConversion = conversionRepository.create(conversion)
        if (newConversion != null) {
            newConversion.toValue = newConversion.fromValue * newConversion.rate
        }
        return newConversion
    }

    fun findByUser(user: Long): List<Conversion> {
        return conversionRepository.findByUser(user)
    }

    private fun hasRate(rate: String): Boolean {
        val rates = arrayOf("EUR", "USD", "BRL", "JPY")
        return rates.contains(rate);
    }

    private fun getRequestAPI(): RequestAPI {
        val client = ApacheClient()

        val request = Request(Method.GET, "http://api.exchangeratesapi.io/v1/latest")
            .query("access_key", "815f5aec19da1282a74e6b2d0a1a8f57")
            .query("base", "EUR")

        val gson = Gson()
        return gson.fromJson(client(request).bodyString(), RequestAPI::class.java)
    }

    private fun getRate(currency: String, rates: Rate): Double {
        return when (currency) {
            "EUR" -> rates.EUR.toDouble()
            "USD" -> rates.USD.toDouble()
            "BRL" -> rates.BRL.toDouble()
            "JPY" -> rates.JPY.toDouble()
            else -> 0.0
        }
    }
}