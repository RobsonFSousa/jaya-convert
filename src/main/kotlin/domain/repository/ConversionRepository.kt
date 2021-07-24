package domain.repository

import domain.Conversion
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.`java-time`.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import javax.sql.DataSource

internal object Conversions : LongIdTable() {
    val user: Column<Long> = long("user")
    val from: Column<String> = char(" from", 3)
    val fromValue: Column<Double> = double("from_value")
    val to: Column<String> = char(" to", 3)
    val toValue: Column<Double> = double("to_value")
    val rate: Column<Double> = double("rate")
    val createdAt = datetime("created_at").clientDefault{ LocalDateTime.now() }

    fun toDomain(row: ResultRow): Conversion {
        return Conversion(
            id = row[Conversions.id].value,
            user = row[Conversions.user],
            from = row[Conversions.from].toString(),
            fromValue = row[Conversions.fromValue].toDouble(),
            to = row[Conversions.to].toString(),
            toValue = row[Conversions.toValue].toDouble(),
            rate = row[Conversions.rate].toDouble(),
            createdAt = row[Conversions.createdAt],
        )
    }
}

class ConversionRepository(private val dataSource: DataSource) {
    init {
        transaction(Database.connect(dataSource)) {
            SchemaUtils.create(Conversions)
            SchemaUtils.create(Users)
        }
    }

    fun create(conversion: Conversion): Conversion? {
        val newId = transaction(Database.connect(dataSource)) {
            Conversions.insertAndGetId { row ->
                row[Conversions.user] = conversion.user
                row[Conversions.from] = conversion.from
                row[Conversions.fromValue] = conversion.fromValue
                row[Conversions.to] = conversion.to
                row[Conversions.toValue] = conversion.toValue
                row[Conversions.rate] = conversion.rate
                row[Conversions.createdAt] = LocalDateTime.now()
            }.value
        }

        return findById(newId)
    }

    fun findAll(): List<Conversion> {
        return transaction(Database.connect(dataSource)) {
            Conversions.selectAll()
                .map { Conversions.toDomain(it) }
        }
    }

    fun findById(id: Long): Conversion? {
        return transaction(Database.connect(dataSource)) {
            Conversions.select { Conversions.id eq id }
                .map { Conversions.toDomain(it) }
                .firstOrNull()
        }
    }
}