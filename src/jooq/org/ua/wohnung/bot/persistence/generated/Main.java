/*
 * This file is generated by jOOQ.
 */
package org.ua.wohnung.bot.persistence.generated;


import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
import org.ua.wohnung.bot.persistence.generated.tables.Account;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Main extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>main</code>
     */
    public static final Main MAIN = new Main();

    /**
     * The table <code>main.account</code>.
     */
    public final Account ACCOUNT = Account.ACCOUNT;

    /**
     * No further instances allowed
     */
    private Main() {
        super("main", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            Account.ACCOUNT
        );
    }
}