package entity

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object User : IntIdTable() {
    val name: Column<String> = varchar("name", 100)
}