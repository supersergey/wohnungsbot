package db.migration

import mu.KotlinLogging
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.jooq.tools.csv.CSVReader
import org.ua.wohnung.bot.persistence.generated.tables.PostCode.POST_CODE
import java.io.InputStream
import java.io.InputStreamReader

class V15__load_data_into_post_codes_table : BaseJavaMigration() {

    private val logger = KotlinLogging.logger { }

    //    Name;PLZ Name (short);PLZ Name (long);Postleitzahl / Post code;Kreis code;Land name;Land code;Kreis name
    //    0   ;1               ;2              ;3                       ;4         ;5        ;6        ;7
    override fun migrate(context: Context) {
        postCodesInputStream("georef-germany-postleitzahl-reduced.csv").use { inputStream ->
            InputStreamReader(inputStream).use { inputStreamReader ->
                CSVReader(inputStreamReader, ';').use { csvReader ->
                    val jooq = DSL.using(context.connection, SQLDialect.POSTGRES)
                    jooq.transaction { _ ->
                        csvReader.readNext()
                        csvReader.forEachRemaining {
                            if (it != null) {
                                kotlin.runCatching {
                                    if (jooq.fetchCount(POST_CODE, POST_CODE.ID.eq(it[0].trim())) == 0) {
                                        jooq.newRecord(POST_CODE).apply {
                                            id = it[0].trim()
                                            plzName = it[1].trim()
                                            kreisCode = it[4].trim()
                                            landName = it[5].trim()
                                            landCode = it[6].trim()
                                            kreisName = it[7].trim()
                                        }.store()
                                    }
                                }.onFailure { throwable ->
                                    logger.error(throwable) { "Failed on record: ${it.joinToString(";")}" }
                                    throw throwable
                                }
                            }
                        }
                    }
                    assert(jooq.fetchOne(POST_CODE, POST_CODE.ID.eq("26489"))?.landName == "Niedersachsen")
                }
            }
        }
    }

    private fun postCodesInputStream(path: String): InputStream =
        this.javaClass.classLoader?.getResourceAsStream(path)
            ?.let {
                return@let it
            } ?: kotlin.run {
            throw IllegalArgumentException("Could not load postcodes from: $path")
        }
}
