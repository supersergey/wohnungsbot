/*
 * This file is generated by jOOQ.
 */
package org.ua.wohnung.bot.persistence.generated.tables;


import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row8;
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
import org.ua.wohnung.bot.persistence.generated.tables.records.ApartmentRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Apartment extends TableImpl<ApartmentRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>main.apartment</code>
     */
    public static final Apartment APARTMENT = new Apartment();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ApartmentRecord> getRecordType() {
        return ApartmentRecord.class;
    }

    /**
     * The column <code>main.apartment.id</code>.
     */
    public final TableField<ApartmentRecord, String> ID = createField(DSL.name("id"), SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>main.apartment.city</code>.
     */
    public final TableField<ApartmentRecord, String> CITY = createField(DSL.name("city"), SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>main.apartment.bundesland</code>.
     */
    public final TableField<ApartmentRecord, String> BUNDESLAND = createField(DSL.name("bundesland"), SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>main.apartment.min_tenants</code>.
     */
    public final TableField<ApartmentRecord, Short> MIN_TENANTS = createField(DSL.name("min_tenants"), SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>main.apartment.max_tenants</code>.
     */
    public final TableField<ApartmentRecord, Short> MAX_TENANTS = createField(DSL.name("max_tenants"), SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>main.apartment.description</code>.
     */
    public final TableField<ApartmentRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>main.apartment.pets_allowed</code>.
     */
    public final TableField<ApartmentRecord, Boolean> PETS_ALLOWED = createField(DSL.name("pets_allowed"), SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>main.apartment.publicationstatus</code>.
     */
    public final TableField<ApartmentRecord, String> PUBLICATIONSTATUS = createField(DSL.name("publicationstatus"), SQLDataType.VARCHAR(128).nullable(false), this, "");

    private Apartment(Name alias, Table<ApartmentRecord> aliased) {
        this(alias, aliased, null);
    }

    private Apartment(Name alias, Table<ApartmentRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>main.apartment</code> table reference
     */
    public Apartment(String alias) {
        this(DSL.name(alias), APARTMENT);
    }

    /**
     * Create an aliased <code>main.apartment</code> table reference
     */
    public Apartment(Name alias) {
        this(alias, APARTMENT);
    }

    /**
     * Create a <code>main.apartment</code> table reference
     */
    public Apartment() {
        this(DSL.name("apartment"), null);
    }

    public <O extends Record> Apartment(Table<O> child, ForeignKey<O, ApartmentRecord> key) {
        super(child, key, APARTMENT);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Main.MAIN;
    }

    @Override
    public UniqueKey<ApartmentRecord> getPrimaryKey() {
        return Keys.APARTMENT_PKEY;
    }

    @Override
    public Apartment as(String alias) {
        return new Apartment(DSL.name(alias), this);
    }

    @Override
    public Apartment as(Name alias) {
        return new Apartment(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Apartment rename(String name) {
        return new Apartment(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Apartment rename(Name name) {
        return new Apartment(name, null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<String, String, String, Short, Short, String, Boolean, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }
}
