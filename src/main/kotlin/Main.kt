import controller.ConversionController
import controller.UserController
import entity.Conversions
import entity.Users
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>) {
    Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Conversions, Users)
    }

    val app = Javalin.create().start(7000)
    app.routes {
        path("v1/conversions") {
            post(ConversionController::convert)
            get(ConversionController::listAll)
        }

        path("v1/users") {
            get(UserController::listAll)
        }
    }
}