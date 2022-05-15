package org.ua.wohnung.bot.persistence.configuration

import org.jooq.ConnectionProvider
import java.sql.Connection
import java.sql.DriverManager

class JdbcConnectionProvider(
    private val url: String,
    private val user: String,
    private val password: String
) : ConnectionProvider {
    override fun acquire(): Connection = DriverManager.getConnection(url, user, password)

    override fun release(connection: Connection) {
        connection.close()
    }
}
