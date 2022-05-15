package org.ua.wohnung.bot.persistence.configuration

import org.jooq.ConnectionProvider
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL

class JooqContextBuilder(
    private val connectionProvider: ConnectionProvider,
    private val dialect: String
) {
    fun build(): DSLContext =
        DSL.using(connectionProvider, SQLDialect.valueOf(dialect))
}
