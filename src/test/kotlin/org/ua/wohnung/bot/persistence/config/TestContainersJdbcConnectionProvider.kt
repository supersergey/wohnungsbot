package org.ua.wohnung.bot.persistence.config

import org.jooq.ConnectionProvider
import org.testcontainers.containers.PostgreSQLContainer
import java.sql.Connection
import java.sql.DriverManager

class TestContainersJdbcConnectionProvider(private val container: PostgreSQLContainer<*>): ConnectionProvider {
    override fun acquire(): Connection = with(container) {
        DriverManager.getConnection(jdbcUrl, username, password)
    }

    override fun release(connection: Connection) {
        connection.close()
    }
}