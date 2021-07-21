package controller

import io.javalin.http.Context
import service.UserService

object UserController {
    fun listAll(ctx: Context) {
        val userService = UserService()
        val users = userService.listAll()
        ctx.json(users)
    }
}