package entity

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.CurrentDateTime
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.math.BigDecimal

object Conversions : IntIdTable() {
    val user = reference("user", Users)
    val from: Column<String> = varchar("from", 3)
    val to: Column<String> = varchar("to", 3)
    val amount: Column<BigDecimal> = decimal("amount", 15, 2)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime())
}

class Conversion (id: EntityID<Int>) : Entity<Int>(id) {
    var user by Conversions.user
    var from by Conversions.from
    var to by Conversions.to
    var amount by Conversions.amount
    var createdAt by Conversions.createdAt
}