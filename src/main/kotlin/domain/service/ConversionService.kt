package domain.service

import domain.Conversion
import domain.repository.ConversionRepository

class ConversionService(val conversionRepository: ConversionRepository) {
    fun create(conversion: Conversion): Conversion? {
        return conversionRepository.create(conversion)
    }

    fun all(): List<Conversion> {
        return conversionRepository.findAll()
    }
}