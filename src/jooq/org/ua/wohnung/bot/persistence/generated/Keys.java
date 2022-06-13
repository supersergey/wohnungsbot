/*
 * This file is generated by jOOQ.
 */
package org.ua.wohnung.bot.persistence.generated;


import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.ua.wohnung.bot.persistence.generated.tables.Account;
import org.ua.wohnung.bot.persistence.generated.tables.Apartment;
import org.ua.wohnung.bot.persistence.generated.tables.ApartmentAccount;
import org.ua.wohnung.bot.persistence.generated.tables.UserDetails;
import org.ua.wohnung.bot.persistence.generated.tables.records.AccountRecord;
import org.ua.wohnung.bot.persistence.generated.tables.records.ApartmentAccountRecord;
import org.ua.wohnung.bot.persistence.generated.tables.records.ApartmentRecord;
import org.ua.wohnung.bot.persistence.generated.tables.records.UserDetailsRecord;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * main.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AccountRecord> ACCOUNT_PKEY = Internal.createUniqueKey(Account.ACCOUNT, DSL.name("account_pkey"), new TableField[] { Account.ACCOUNT.ID }, true);
    public static final UniqueKey<ApartmentRecord> APARTMENT_PKEY = Internal.createUniqueKey(Apartment.APARTMENT, DSL.name("apartment_pkey"), new TableField[] { Apartment.APARTMENT.ID }, true);
    public static final UniqueKey<ApartmentAccountRecord> APARTMENT_ACCOUNT_PKEY = Internal.createUniqueKey(ApartmentAccount.APARTMENT_ACCOUNT, DSL.name("apartment_account_pkey"), new TableField[] { ApartmentAccount.APARTMENT_ACCOUNT.ACCOUNT_ID, ApartmentAccount.APARTMENT_ACCOUNT.APARTMENT_ID }, true);
    public static final UniqueKey<UserDetailsRecord> USER_DETAILS_PKEY = Internal.createUniqueKey(UserDetails.USER_DETAILS, DSL.name("user_details_pkey"), new TableField[] { UserDetails.USER_DETAILS.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<ApartmentAccountRecord, AccountRecord> APARTMENT_ACCOUNT__APARTMENT_ACCOUNT_ACCOUNT_ID_FKEY = Internal.createForeignKey(ApartmentAccount.APARTMENT_ACCOUNT, DSL.name("apartment_account_account_id_fkey"), new TableField[] { ApartmentAccount.APARTMENT_ACCOUNT.ACCOUNT_ID }, Keys.ACCOUNT_PKEY, new TableField[] { Account.ACCOUNT.ID }, true);
    public static final ForeignKey<ApartmentAccountRecord, ApartmentRecord> APARTMENT_ACCOUNT__APARTMENT_ACCOUNT_APARTMENT_ID_FKEY = Internal.createForeignKey(ApartmentAccount.APARTMENT_ACCOUNT, DSL.name("apartment_account_apartment_id_fkey"), new TableField[] { ApartmentAccount.APARTMENT_ACCOUNT.APARTMENT_ID }, Keys.APARTMENT_PKEY, new TableField[] { Apartment.APARTMENT.ID }, true);
    public static final ForeignKey<UserDetailsRecord, AccountRecord> USER_DETAILS__USER_DETAILS_ID_FKEY = Internal.createForeignKey(UserDetails.USER_DETAILS, DSL.name("user_details_id_fkey"), new TableField[] { UserDetails.USER_DETAILS.ID }, Keys.ACCOUNT_PKEY, new TableField[] { Account.ACCOUNT.ID }, true);
}
