package service

import dto.UserDTO
import entity.Users
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserService {
    fun listAll(): List<UserDTO> {
        var users = ArrayList<UserDTO>()
        transaction {
            users = Users.selectAll().map {
                UserDTO(it[Users.id].value, it[Users.name])
            } as ArrayList<UserDTO>
        }
        return users
    }

}