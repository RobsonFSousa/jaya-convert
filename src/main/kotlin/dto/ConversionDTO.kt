package dto

import java.math.BigDecimal
import java.time.LocalDateTime

class ConversionDTO {
    var id: Int = 0
    var user: UserDTO = UserDTO(0, "")
    var from: String = ""
    var to: String = ""
    var amount: BigDecimal = BigDecimal(0)
    var createdAt: LocalDateTime = LocalDateTime.now()

    constructor(id: Int, user: UserDTO, from: String, to: String, amount: BigDecimal, createdAt: LocalDateTime) {
        this.id = id
        this.user = user
        this.from = from
        this.to = to
        this.amount = amount
        this.createdAt = createdAt
    }
}