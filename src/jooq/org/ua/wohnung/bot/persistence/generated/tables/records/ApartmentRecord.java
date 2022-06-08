/*
 * This file is generated by jOOQ.
 */
package org.ua.wohnung.bot.persistence.generated.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;
import org.ua.wohnung.bot.persistence.generated.tables.Apartment;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ApartmentRecord extends UpdatableRecordImpl<ApartmentRecord> implements Record8<Long, String, String, Short, Short, String, Boolean, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>main.apartment.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>main.apartment.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>main.apartment.city</code>.
     */
    public void setCity(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>main.apartment.city</code>.
     */
    public String getCity() {
        return (String) get(1);
    }

    /**
     * Setter for <code>main.apartment.bundesland</code>.
     */
    public void setBundesland(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>main.apartment.bundesland</code>.
     */
    public String getBundesland() {
        return (String) get(2);
    }

    /**
     * Setter for <code>main.apartment.min_tenants</code>.
     */
    public void setMinTenants(Short value) {
        set(3, value);
    }

    /**
     * Getter for <code>main.apartment.min_tenants</code>.
     */
    public Short getMinTenants() {
        return (Short) get(3);
    }

    /**
     * Setter for <code>main.apartment.max_tenants</code>.
     */
    public void setMaxTenants(Short value) {
        set(4, value);
    }

    /**
     * Getter for <code>main.apartment.max_tenants</code>.
     */
    public Short getMaxTenants() {
        return (Short) get(4);
    }

    /**
     * Setter for <code>main.apartment.description</code>.
     */
    public void setDescription(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>main.apartment.description</code>.
     */
    public String getDescription() {
        return (String) get(5);
    }

    /**
     * Setter for <code>main.apartment.pets_allowed</code>.
     */
    public void setPetsAllowed(Boolean value) {
        set(6, value);
    }

    /**
     * Getter for <code>main.apartment.pets_allowed</code>.
     */
    public Boolean getPetsAllowed() {
        return (Boolean) get(6);
    }

    /**
     * Setter for <code>main.apartment.publicationstatus</code>.
     */
    public void setPublicationstatus(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>main.apartment.publicationstatus</code>.
     */
    public String getPublicationstatus() {
        return (String) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row8<Long, String, String, Short, Short, String, Boolean, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    @Override
    public Row8<Long, String, String, Short, Short, String, Boolean, String> valuesRow() {
        return (Row8) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Apartment.APARTMENT.ID;
    }

    @Override
    public Field<String> field2() {
        return Apartment.APARTMENT.CITY;
    }

    @Override
    public Field<String> field3() {
        return Apartment.APARTMENT.BUNDESLAND;
    }

    @Override
    public Field<Short> field4() {
        return Apartment.APARTMENT.MIN_TENANTS;
    }

    @Override
    public Field<Short> field5() {
        return Apartment.APARTMENT.MAX_TENANTS;
    }

    @Override
    public Field<String> field6() {
        return Apartment.APARTMENT.DESCRIPTION;
    }

    @Override
    public Field<Boolean> field7() {
        return Apartment.APARTMENT.PETS_ALLOWED;
    }

    @Override
    public Field<String> field8() {
        return Apartment.APARTMENT.PUBLICATIONSTATUS;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getCity();
    }

    @Override
    public String component3() {
        return getBundesland();
    }

    @Override
    public Short component4() {
        return getMinTenants();
    }

    @Override
    public Short component5() {
        return getMaxTenants();
    }

    @Override
    public String component6() {
        return getDescription();
    }

    @Override
    public Boolean component7() {
        return getPetsAllowed();
    }

    @Override
    public String component8() {
        return getPublicationstatus();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getCity();
    }

    @Override
    public String value3() {
        return getBundesland();
    }

    @Override
    public Short value4() {
        return getMinTenants();
    }

    @Override
    public Short value5() {
        return getMaxTenants();
    }

    @Override
    public String value6() {
        return getDescription();
    }

    @Override
    public Boolean value7() {
        return getPetsAllowed();
    }

    @Override
    public String value8() {
        return getPublicationstatus();
    }

    @Override
    public ApartmentRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public ApartmentRecord value2(String value) {
        setCity(value);
        return this;
    }

    @Override
    public ApartmentRecord value3(String value) {
        setBundesland(value);
        return this;
    }

    @Override
    public ApartmentRecord value4(Short value) {
        setMinTenants(value);
        return this;
    }

    @Override
    public ApartmentRecord value5(Short value) {
        setMaxTenants(value);
        return this;
    }

    @Override
    public ApartmentRecord value6(String value) {
        setDescription(value);
        return this;
    }

    @Override
    public ApartmentRecord value7(Boolean value) {
        setPetsAllowed(value);
        return this;
    }

    @Override
    public ApartmentRecord value8(String value) {
        setPublicationstatus(value);
        return this;
    }

    @Override
    public ApartmentRecord values(Long value1, String value2, String value3, Short value4, Short value5, String value6, Boolean value7, String value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ApartmentRecord
     */
    public ApartmentRecord() {
        super(Apartment.APARTMENT);
    }

    /**
     * Create a detached, initialised ApartmentRecord
     */
    public ApartmentRecord(Long id, String city, String bundesland, Short minTenants, Short maxTenants, String description, Boolean petsAllowed, String publicationstatus) {
        super(Apartment.APARTMENT);

        setId(id);
        setCity(city);
        setBundesland(bundesland);
        setMinTenants(minTenants);
        setMaxTenants(maxTenants);
        setDescription(description);
        setPetsAllowed(petsAllowed);
        setPublicationstatus(publicationstatus);
    }

    /**
     * Create a detached, initialised ApartmentRecord
     */
    public ApartmentRecord(org.ua.wohnung.bot.persistence.generated.tables.pojos.Apartment value) {
        super(Apartment.APARTMENT);

        if (value != null) {
            setId(value.getId());
            setCity(value.getCity());
            setBundesland(value.getBundesland());
            setMinTenants(value.getMinTenants());
            setMaxTenants(value.getMaxTenants());
            setDescription(value.getDescription());
            setPetsAllowed(value.getPetsAllowed());
            setPublicationstatus(value.getPublicationstatus());
        }
    }
}