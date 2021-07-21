package dto

import java.math.BigDecimal

class ConversionDTO {
    var id: Int = 0
    var user: UserDTO = UserDTO()
    var from: String = ""
    var to: String = ""
    var amount: BigDecimal = BigDecimal(0)
}