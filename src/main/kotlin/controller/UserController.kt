package controller

import domain.UserDTO
import io.javalin.http.Context
import domain.service.UserService

class UserController(private val userService: UserService) {

    fun create(ctx: Context) {
        ctx.bodyValidator<UserDTO>()
            .check({ it.user != null })
            .check({ !it.user?.name.isNullOrBlank() })
            .get().user?.also { user ->
                userService.create(user).apply {
                    ctx.json(UserDTO(this))
                }
            }
    }

    fun all(ctx: Context) {
        val users = userService.all()
        ctx.json(users)
    }
}