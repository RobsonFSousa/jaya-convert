package domain.repository

import domain.User
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

internal object Users : LongIdTable() {
    val name: Column<String> = varchar(" name", 100)

    fun toDomain(row: ResultRow): User {
        return User(
            id = row[Users.id].value,
            name = row[Users.name],
        )
    }
}

class UserRepository(private val dataSource: DataSource) {
    init {
        transaction(Database.connect(dataSource)) {
            SchemaUtils.create(Users)
        }
    }

    fun findAll(): List<User> {
        return transaction(Database.connect(dataSource)) {
            Users.selectAll()
                .map { Users.toDomain(it) }
        }
    }

    fun create(user: User): Long? {
        return transaction(Database.connect(dataSource)) {
            Users.insertAndGetId { row ->
                row[Users.name] = user.name
            }.value
        }
    }
}