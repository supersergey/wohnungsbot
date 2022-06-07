package org.ua.wohnung.bot.sheets

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.nio.file.Path
import kotlin.io.path.pathString

class SheetProperties(
    private val objectMapper: ObjectMapper,
    path: Path
) {
    private val map: Map<String, String> =
        requireNotNull(
            this.javaClass.classLoader?.getResourceAsStream(path.pathString)
        ).let {
            objectMapper.readValue(it)
        }

    val spreadsheetId: String by map
    val range: String by map
}
