package org.ua.wohnung.bot.persistence.config

import java.sql.Connection

class DatabaseCleaner {
    private val tablesToIgnore = listOf(
        TableData("databasechangelog"),
        TableData("databasechangeloglock")
    )

    data class TableData(val name: String, val schema: String? = "public") {
        val fullyQualifiedTableName =
            if (schema != null) "$schema.$name" else name
    }

    fun cleanUp(connection: Connection) {
        val tablesNames = connection.tableNames()
        if (tablesNames.isEmpty()) {
            return
        }
        val stringBuilder = StringBuilder("TRUNCATE ")
        for (i in tablesNames.indices) {
            if (i == 0) {
                stringBuilder.append(tablesNames[i].fullyQualifiedTableName)
            } else {
                stringBuilder
                    .append(", ")
                    .append(tablesNames[i].fullyQualifiedTableName)
            }
        }
        connection.prepareStatement(stringBuilder.toString())
            .execute()
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