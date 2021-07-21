package entity

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.math.BigDecimal

object Conversion : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    var user = reference("user", User)
    val from: Column<String> = varchar("from", 3)
    val to: Column<String> = varchar("to", 3)
    val amount: Column<BigDecimal> = decimal("amount", 15, 2)
    override val primaryKey = PrimaryKey(id, name = "PK_Conversion_Id")
}