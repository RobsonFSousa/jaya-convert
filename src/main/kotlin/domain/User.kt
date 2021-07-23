package domain

data class UserDTO(val user: User? = null)

data class User(val id: Long? = null,
                val name: String)