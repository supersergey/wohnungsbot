package org.ua.wohnung.bot.persistence.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.koin.core.context.startKoin
import org.koin.core.logger.PrintLogger
import org.koin.dsl.module
import org.koin.fileProperties
import org.koin.java.KoinJavaComponent.inject
import org.testcontainers.containers.PostgreSQLContainer
import org.ua.wohnung.bot.persistence.AccountRepository
import org.ua.wohnung.bot.persistence.configuration.JooqContextBuilder
import org.ua.wohnung.bot.security.Secrets
import javax.sql.DataSource

class JooqExtension : BeforeAllCallback, BeforeEachCallback, AfterAllCallback {

    private val cleaner = DatabaseCleaner()

    private val jooqModule = module {
        single {
            PostgreSQLContainer("postgres:14.2")
                .withDatabaseName("foo")
                .withUsername("foo")
                .withPassword("secret")
        }
        single<DataSource> {
            val container = get<PostgreSQLContainer<*>>()
            HikariDataSource(
                HikariConfig().apply {
                    username = container.username
                    password = container.password
                    jdbcUrl = container.jdbcUrl
                }
            )
        }
        single {
            JooqContextBuilder(
                get(),
                SQLDialect.valueOf(requireNotNull(getKoin().getProperty(Secrets.SQL_DIALECT.setting)))
            ).build()
        }

        single { AccountRepository(get()) }
    }

    override fun beforeAll(context: ExtensionContext) {
        startKoin {
            printLogger()
            logger(PrintLogger())
            modules(
                jooqModule
            )
            fileProperties("/secrets/secrets-test.properties")
        }

        val postgreSQLContainer: PostgreSQLContainer<*> by inject(PostgreSQLContainer::class.java)
        postgreSQLContainer.start()
        runFlyWay(postgreSQLContainer)
    }

    override fun afterAll(context: ExtensionContext) {
        val postgreSQLContainer: PostgreSQLContainer<*> by inject(PostgreSQLContainer::class.java)
        postgreSQLContainer.stop()
    }

    override fun beforeEach(context: ExtensionContext) {
        val jooq: DSLContext by inject(DSLContext::class.java)

        jooq.connection {
            it.autoCommit = false
            cleaner.cleanUp(it)
            it.commit()
        }
    }

    private fun runFlyWay(postgreSQLContainer: PostgreSQLContainer<*>) {
        Flyway.configure().dataSource(
            postgreSQLContainer.jdbcUrl,
            postgreSQLContainer.username,
            postgreSQLContainer.password
        )
            .schemas("main")
            .createSchemas(true)
            .load()
            .run {
                clean()
                migrate()
            }
    }
}
