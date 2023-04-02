package org.ua.wohnung.bot.persistence

import org.jooq.DSLContext
import org.ua.wohnung.bot.persistence.generated.Tables.POST_CODE
import org.ua.wohnung.bot.persistence.generated.tables.pojos.PostCode

class PostCodeRepository(private val dslContext: DSLContext) {
    fun findByPostCode(postCode: String): PostCode? =
        dslContext.fetchOne(POST_CODE, POST_CODE.ID.eq(postCode))?.into(PostCode::class.java)
}
