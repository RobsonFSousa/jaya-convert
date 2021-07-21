import controller.ConversionController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*

fun main(args: Array<String>) {

    val app = Javalin.create().start(7000)
    app.routes {
        path("v1/conversions") {
            post(ConversionController::convert)
            get(ConversionController::listAll)
        }
    }
}