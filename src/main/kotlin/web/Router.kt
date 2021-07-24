package web

import web.controller.ConversionController
import web.controller.UserController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import org.koin.standalone.KoinComponent

class Router(
    private val userController: UserController,
    private val conversionController: ConversionController,
) : KoinComponent {

    fun register(app: Javalin) {
        app.routes {
            path("v1/users") {
                get(userController::all)
                post(userController::create)
            }

            path("v1/conversions") {
                post(conversionController::create)
                path(":user") {
                    get(conversionController::findByUser)
                }
            }
        }
    }
}
