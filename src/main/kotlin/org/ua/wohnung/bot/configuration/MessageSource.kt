package org.ua.wohnung.bot.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.ua.wohnung.bot.flows.userregistration.FlowStep
import java.nio.file.Path
import kotlin.io.path.pathString

class MessageSource(objectMapper: ObjectMapper, resourceClassPath: Path) {
    private val internalMap: Map<FlowStep, String>

    init {
        val inputStream = requireNotNull(
            javaClass.classLoader.getResourceAsStream(resourceClassPath.pathString)
        )
        internalMap = inputStream.use { objectMapper.readValue(it) }
    }

    operator fun get(id: FlowStep): String =
        requireNotNull(internalMap[id])
}
