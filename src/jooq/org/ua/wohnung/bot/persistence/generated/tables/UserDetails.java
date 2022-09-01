/*
 * This file is generated by jOOQ.
 */
package org.ua.wohnung.bot.persistence.generated.tables;


import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row12;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.ua.wohnung.bot.persistence.generated.Keys;
import org.ua.wohnung.bot.persistence.generated.Main;
import org.ua.wohnung.bot.persistence.generated.tables.records.UserDetailsRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserDetails extends TableImpl<UserDetailsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>main.user_details</code>
     */
    public static final UserDetails USER_DETAILS = new UserDetails();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserDetailsRecord> getRecordType() {
        return UserDetailsRecord.class;
    }

    /**
     * The column <code>main.user_details.id</code>.
     */
    public final TableField<UserDetailsRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>main.user_details.first_last_name</code>.
     */
    public final TableField<UserDetailsRecord, String> FIRST_LAST_NAME = createField(DSL.name("first_last_name"), SQLDataType.VARCHAR(128), this, "");

    /**
     * The column <code>main.user_details.phone</code>.
     */
    public final TableField<UserDetailsRecord, String> PHONE = createField(DSL.name("phone"), SQLDataType.VARCHAR(128), this, "");

    /**
     * The column <code>main.user_details.number_of_tenants</code>.
     */
    public final TableField<UserDetailsRecord, Short> NUMBER_OF_TENANTS = createField(DSL.name("number_of_tenants"), SQLDataType.SMALLINT, this, "");

    /**
     * The column <code>main.user_details.pets</code>.
     */
    public final TableField<UserDetailsRecord, Boolean> PETS = createField(DSL.name("pets"), SQLDataType.BOOLEAN, this, "");

    /**
     * The column <code>main.user_details.bundesland</code>.
     */
    public final TableField<UserDetailsRecord, String> BUNDESLAND = createField(DSL.name("bundesland"), SQLDataType.VARCHAR(128), this, "");

    /**
     * The column <code>main.user_details.district</code>.
     */
    public final TableField<UserDetailsRecord, String> DISTRICT = createField(DSL.name("district"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>main.user_details.family_members</code>.
     */
    public final TableField<UserDetailsRecord, String> FAMILY_MEMBERS = createField(DSL.name("family_members"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>main.user_details.ready_to_move</code>.
     */
    public final TableField<UserDetailsRecord, Boolean> READY_TO_MOVE = createField(DSL.name("ready_to_move"), SQLDataType.BOOLEAN, this, "");

    /**
     * The column <code>main.user_details.foreign_languages</code>.
     */
    public final TableField<UserDetailsRecord, String> FOREIGN_LANGUAGES = createField(DSL.name("foreign_languages"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>main.user_details.allergies</code>.
     */
    public final TableField<UserDetailsRecord, String> ALLERGIES = createField(DSL.name("allergies"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>main.user_details.email</code>.
     */
    public final TableField<UserDetailsRecord, String> EMAIL = createField(DSL.name("email"), SQLDataType.VARCHAR(1024), this, "");

    private UserDetails(Name alias, Table<UserDetailsRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserDetails(Name alias, Table<UserDetailsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>main.user_details</code> table reference
     */
    public UserDetails(String alias) {
        this(DSL.name(alias), USER_DETAILS);
    }

    /**
     * Create an aliased <code>main.user_details</code> table reference
     */
    public UserDetails(Name alias) {
        this(alias, USER_DETAILS);
    }

    /**
     * Create a <code>main.user_details</code> table reference
     */
    public UserDetails() {
        this(DSL.name("user_details"), null);
    }

    public <O extends Record> UserDetails(Table<O> child, ForeignKey<O, UserDetailsRecord> key) {
        super(child, key, USER_DETAILS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Main.MAIN;
    }

    @Override
    public UniqueKey<UserDetailsRecord> getPrimaryKey() {
        return Keys.USER_DETAILS_PKEY;
    }

    @Override
    public List<ForeignKey<UserDetailsRecord, ?>> getReferences() {
        return Arrays.asList(Keys.USER_DETAILS__USER_DETAILS_ID_FKEY);
    }

    private transient Account _account;

    /**
     * Get the implicit join path to the <code>main.account</code> table.
     */
    public Account account() {
        if (_account == null)
            _account = new Account(this, Keys.USER_DETAILS__USER_DETAILS_ID_FKEY);

        return _account;
    }

    @Override
    public UserDetails as(String alias) {
        return new UserDetails(DSL.name(alias), this);
    }

    @Override
    public UserDetails as(Name alias) {
        return new UserDetails(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserDetails rename(String name) {
        return new UserDetails(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserDetails rename(Name name) {
        return new UserDetails(name, null);
    }

    // -------------------------------------------------------------------------
    // Row12 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row12<Long, String, String, Short, Boolean, String, String, String, Boolean, String, String, String> fieldsRow() {
        return (Row12) super.fieldsRow();
    }
}
