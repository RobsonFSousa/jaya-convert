import controller.ConversionController
import controller.UserController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import org.koin.standalone.KoinComponent

class Router(
    private val userController: UserController,
    private val conversionContoller: ConversionController,
) : KoinComponent {

    fun register(app: Javalin) {
        app.routes {
            path("v1/users") {
                get(userController::all)
                post(userController::create)
            }

            path("v1/conversions") {
                get(conversionContoller::all)
                post(conversionContoller::create)
            }
        }
    }
}
