package service

import dto.ConversionDTO
import dto.UserDTO
import entity.Conversions
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class ConversionService {
    fun listAll(): List<ConversionDTO> {
        var conversions = ArrayList<ConversionDTO>()
        transaction {
            conversions = Conversions.selectAll().map {
                ConversionDTO(it[Conversions.id].value,
                              it[Conversions.user] as UserDTO,
                              it[Conversions.from],
                              it[Conversions.to],
                              it[Conversions.amount],
                              it[Conversions.createdAt])
            } as ArrayList<ConversionDTO>
        }
        return conversions
    }
}