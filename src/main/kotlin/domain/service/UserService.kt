package domain.service

import domain.User
import domain.repository.UserRepository

class UserService(private val userRepository: UserRepository) {
    fun all(): List<User> {
        return userRepository.findAll()
    }

    fun create(user: User): User {
        val newId = userRepository.create(user)
        return user.copy(id = newId)
    }

}