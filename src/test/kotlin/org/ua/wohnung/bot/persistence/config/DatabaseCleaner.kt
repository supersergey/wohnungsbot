package org.ua.wohnung.bot.persistence.config

import java.sql.Connection

class DatabaseCleaner {
    private val tablesToIgnore = listOf(
        TableData("databasechangelog"),
        TableData("databasechangeloglock")
    )

    data class TableData(val name: String, val schema: String? = "public")

    fun cleanUp(connection: Connection) {
        val tablesNames =
            connection.tableNames().filterNot { it.name == "post_code" }
                .joinToString(",") { "${it.schema}.${it.name}" }
        if (tablesNames.isEmpty()) {
            return
        }
        connection.prepareStatement("TRUNCATE $tablesNames").execute()
    }

    private fun Connection.tableNames(): List<TableData> {
        val databaseMetaData = metaData
        val resultSet = databaseMetaData.getTables(
            catalog, null, null, arrayOf("TABLE")
        )

        val tablesToClean = mutableListOf<TableData>()
        while (resultSet.next()) {
            val table = TableData(
                schema = resultSet.getString("TABLE_SCHEM"),
                name = resultSet.getString("TABLE_NAME")
            )

            if (!tablesToIgnore.contains(table)) {
                tablesToClean.add(table)
            }
        }
        return tablesToClean.toList()
    }
}
