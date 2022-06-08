package org.ua.wohnung.bot.sheets

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject
import org.ua.wohnung.bot.configuration.commonModule
import org.ua.wohnung.bot.configuration.sheetReaderModule

internal class SheetReaderTest {

    private val sheetReader: SheetReader by inject(SheetReader::class.java)

    @BeforeEach
    internal fun setUp() {
        startKoin {
            modules(
                commonModule,
                sheetReaderModule
            )
        }
    }

    @Test
    fun `should read data from a file`() {
        val mapper = RowMapper()

        sheetReader.readRows(0, 5).mapNotNull { row ->
            kotlin.runCatching {
                mapper(row)
            }.getOrNull()
        }.forEach {
            println(it)
        }
    }
}
