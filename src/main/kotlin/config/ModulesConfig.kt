package config

import web.Router
import web.controller.ConversionController
import web.controller.UserController
import domain.repository.ConversionRepository
import domain.repository.UserRepository
import domain.service.ConversionService
import org.koin.dsl.module.module
import domain.service.UserService

object ModulesConfig {
    private val configModule = module {
        single { AppConfig() }
        single {
            DbConfig(getProperty("jdbc.url"), getProperty("db.username"), getProperty("db.password")).getDataSource()
        }
        single { Router(get(), get()) }
    }

    private val userModule = module {
        single { UserController(get()) }
        single { UserService(get()) }
        single { UserRepository(get()) }
    }

    private val conversionModule = module {
        single { ConversionController(get()) }
        single { ConversionService(get()) }
        single { ConversionRepository(get()) }
    }

    internal val allModules = listOf(ModulesConfig.configModule, ModulesConfig.userModule, ModulesConfig.conversionModule)
}
