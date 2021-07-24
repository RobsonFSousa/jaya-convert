package web.controller

import config.AppConfig
import domain.ConversionDTO
import domain.ConversionsDTO
import io.javalin.Javalin
import kong.unirest.HttpResponse
import kong.unirest.Unirest
import org.eclipse.jetty.http.HttpStatus
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConversionControllerTest {
    private lateinit var app: Javalin

    @BeforeAll
    fun start() {
        app = AppConfig().setup().start()
    }

    @AfterAll
    fun stop() {
        app.stop()
    }

    @Test
    fun `POST conversion should returns conversion by rate`() {
        val user = "1"
        val response: HttpResponse<ConversionDTO> = Unirest.post("http://localhost:7000/api/v1/conversions/")
            .body("{ \"conversion\": {\"user\":\"${user}\", \"from\":\"USD\", \"fromValue\":\"5.34\", \"to\":\"BRL\" } }")
            .asObject(ConversionDTO::class.java)
        assertEquals(HttpStatus.CREATED_201, response.status)
        assertNotNull(response.body)
        assertEquals(response.body.conversion?.id, 1)
    }

    @Test
    fun `POST conversion should returns BadRequestResponse for empty 'user' param`() {
        val response: HttpResponse<String> = Unirest.post("http://localhost:7000/api/v1/conversions/")
            .body("{ \"conversion\": {\"from\":\"USD\", \"to\":\"BRL\" } }")
            .asString()
        assertEquals(HttpStatus.BAD_REQUEST_400, response.status)
        assertNotNull(response.body)
    }

    @Test
    fun `POST conversion should returns BadRequestResponse for empty 'from' param`() {
        val user = "1"
        val response: HttpResponse<String> = Unirest.post("http://localhost:7000/api/v1/conversions/")
            .body("{ \"conversion\": {\"user\":\"${user}\", \"fromValue\":\"5.34\", \"to\":\"BRL\" } }")
            .asString()
        assertEquals(HttpStatus.BAD_REQUEST_400, response.status)
        assertNotNull(response.body)
    }

    @Test
    fun `POST conversion should returns BadRequestResponse for empty 'fromValue' param`() {
        val user = "1"
        val response: HttpResponse<String> = Unirest.post("http://localhost:7000/api/v1/conversions/")
            .body("{ \"conversion\": {\"user\":\"${user}\", \"from\":\"USD\", \"to\":\"BRL\" } }")
            .asString()
        assertEquals(HttpStatus.BAD_REQUEST_400, response.status)
        assertNotNull(response.body)
    }

    @Test
    fun `POST conversion should returns BadRequestResponse for empty 'to' param`() {
        val user = "1"
        val response: HttpResponse<String> = Unirest.post("http://localhost:7000/api/v1/conversions/")
            .body("{ \"conversion\": {\"user\":\"${user}\", \"from\":\"USD\",  } }")
            .asString()
        assertEquals(HttpStatus.BAD_REQUEST_400, response.status)
        assertNotNull(response.body)
    }

    @Test
    fun `GET conversions by user should fetch list of conversions`() {
        val user = "123"
        val count = 10
        for (i in 1..count) {
            Unirest.post("http://localhost:7000/api/v1/conversions/")
                .body("{ \"conversion\": {\"user\":\"${user}\", \"from\":\"USD\", \"fromValue\":\"5.34\", \"to\":\"BRL\" } }")
                .asObject(ConversionDTO::class.java)
        }

        val response: HttpResponse<ConversionsDTO>? = Unirest.get("http://localhost:7000/api/v1/conversions/${user}").asObject(ConversionsDTO::class.java)
        if (response != null) {
            assertEquals(HttpStatus.OK_200, response.status)
            assertNotNull(response.body.conversions)
            assertEquals(count, response.body.conversions?.size)
        }
    }

    @Test
    fun `GET conversions by user should return none conversions`() {
        val user = "50"

        val response: HttpResponse<ConversionsDTO>? = Unirest.get("http://localhost:7000/api/v1/conversions/${user}").asObject(ConversionsDTO::class.java)
        if (response != null) {
            assertEquals(HttpStatus.OK_200, response.status)
            assertNotNull(response.body.conversions)
            println(response.body.conversions)
            assertEquals(0, response.body.conversions?.size)
        }
    }
}