package config

import web.Router
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JSR310Module
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import org.eclipse.jetty.server.Server
import org.koin.core.KoinProperties
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.getProperty
import org.koin.standalone.inject
import java.text.SimpleDateFormat
import config.ModulesConfig.allModules
import web.ErrorExceptionMapping

class AppConfig : KoinComponent {
    private val router: Router by inject()

    fun setup(): Javalin {
        StandAloneContext.startKoin(
                allModules,
                KoinProperties(true, true)
        )
        this.configureMapper()
        val app = Javalin.create { config ->
            config.apply {
                enableWebjars()
                enableCorsForAllOrigins()
                contextPath = getProperty("context")
                //addStaticFiles("/swagger")
                //addSinglePageRoot("","/swagger/swagger-ui.html")
                server {
                    Server(getProperty("server_port") as Int)
                }
            }
        }.events {
            it.serverStopping {
                StandAloneContext.stopKoin()
            }
        }
        router.register(app)
        ErrorExceptionMapping.register(app)
        return app
    }

    private fun configureMapper() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        JavalinJackson.configure(
                jacksonObjectMapper()
                    .registerModule(JSR310Module())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                    .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                    .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true)
                    .setDateFormat(dateFormat)
        )
    }
}

