package org.ua.wohnung.bot.persistence.configuration

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import javax.sql.DataSource

class JooqContextBuilder(
    private val dataSource: DataSource,
    private val dialect: SQLDialect
) {
    fun build(): DSLContext =
        DSL.using(dataSource, dialect)
}
