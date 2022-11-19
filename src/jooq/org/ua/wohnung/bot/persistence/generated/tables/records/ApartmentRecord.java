/*
 * This file is generated by jOOQ.
 */
package org.ua.wohnung.bot.persistence.generated.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record14;
import org.jooq.Row14;
import org.jooq.impl.UpdatableRecordImpl;
import org.ua.wohnung.bot.persistence.generated.tables.Apartment;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ApartmentRecord extends UpdatableRecordImpl<ApartmentRecord> implements Record14<String, String, String, Short, Short, String, Boolean, String, String, String, String, Boolean, String, Short> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>main.apartment.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>main.apartment.id</code>.
     */
    public String getId() {
        return (String) get(0);
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

    /**
     * Setter for <code>main.apartment.etage</code>.
     */
    public void setEtage(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>main.apartment.etage</code>.
     */
    public String getEtage() {
        return (String) get(8);
    }

    /**
     * Setter for <code>main.apartment.map_location</code>.
     */
    public void setMapLocation(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>main.apartment.map_location</code>.
     */
    public String getMapLocation() {
        return (String) get(9);
    }

    /**
     * Setter for <code>main.apartment.showing_date</code>.
     */
    public void setShowingDate(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>main.apartment.showing_date</code>.
     */
    public String getShowingDate() {
        return (String) get(10);
    }

    /**
     * Setter for <code>main.apartment.wbs</code>.
     */
    public void setWbs(Boolean value) {
        set(11, value);
    }

    /**
     * Getter for <code>main.apartment.wbs</code>.
     */
    public Boolean getWbs() {
        return (Boolean) get(11);
    }

    /**
     * Setter for <code>main.apartment.wbs_details</code>.
     */
    public void setWbsDetails(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>main.apartment.wbs_details</code>.
     */
    public String getWbsDetails() {
        return (String) get(12);
    }

    /**
     * Setter for <code>main.apartment.number_of_rooms</code>.
     */
    public void setNumberOfRooms(Short value) {
        set(13, value);
    }

    /**
     * Getter for <code>main.apartment.number_of_rooms</code>.
     */
    public Short getNumberOfRooms() {
        return (Short) get(13);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record14 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row14<String, String, String, Short, Short, String, Boolean, String, String, String, String, Boolean, String, Short> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    @Override
    public Row14<String, String, String, Short, Short, String, Boolean, String, String, String, String, Boolean, String, Short> valuesRow() {
        return (Row14) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
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
    public Field<String> field9() {
        return Apartment.APARTMENT.ETAGE;
    }

    @Override
    public Field<String> field10() {
        return Apartment.APARTMENT.MAP_LOCATION;
    }

    @Override
    public Field<String> field11() {
        return Apartment.APARTMENT.SHOWING_DATE;
    }

    @Override
    public Field<Boolean> field12() {
        return Apartment.APARTMENT.WBS;
    }

    @Override
    public Field<String> field13() {
        return Apartment.APARTMENT.WBS_DETAILS;
    }

    @Override
    public Field<Short> field14() {
        return Apartment.APARTMENT.NUMBER_OF_ROOMS;
    }

    @Override
    public String component1() {
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
    public String component9() {
        return getEtage();
    }

    @Override
    public String component10() {
        return getMapLocation();
    }

    @Override
    public String component11() {
        return getShowingDate();
    }

    @Override
    public Boolean component12() {
        return getWbs();
    }

    @Override
    public String component13() {
        return getWbsDetails();
    }

    @Override
    public Short component14() {
        return getNumberOfRooms();
    }

    @Override
    public String value1() {
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
    public String value9() {
        return getEtage();
    }

    @Override
    public String value10() {
        return getMapLocation();
    }

    @Override
    public String value11() {
        return getShowingDate();
    }

    @Override
    public Boolean value12() {
        return getWbs();
    }

    @Override
    public String value13() {
        return getWbsDetails();
    }

    @Override
    public Short value14() {
        return getNumberOfRooms();
    }

    @Override
    public ApartmentRecord value1(String value) {
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
    public ApartmentRecord value9(String value) {
        setEtage(value);
        return this;
    }

    @Override
    public ApartmentRecord value10(String value) {
        setMapLocation(value);
        return this;
    }

    @Override
    public ApartmentRecord value11(String value) {
        setShowingDate(value);
        return this;
    }

    @Override
    public ApartmentRecord value12(Boolean value) {
        setWbs(value);
        return this;
    }

    @Override
    public ApartmentRecord value13(String value) {
        setWbsDetails(value);
        return this;
    }

    @Override
    public ApartmentRecord value14(Short value) {
        setNumberOfRooms(value);
        return this;
    }

    @Override
    public ApartmentRecord values(String value1, String value2, String value3, Short value4, Short value5, String value6, Boolean value7, String value8, String value9, String value10, String value11, Boolean value12, String value13, Short value14) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
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
    public ApartmentRecord(String id, String city, String bundesland, Short minTenants, Short maxTenants, String description, Boolean petsAllowed, String publicationstatus, String etage, String mapLocation, String showingDate, Boolean wbs, String wbsDetails, Short numberOfRooms) {
        super(Apartment.APARTMENT);

        setId(id);
        setCity(city);
        setBundesland(bundesland);
        setMinTenants(minTenants);
        setMaxTenants(maxTenants);
        setDescription(description);
        setPetsAllowed(petsAllowed);
        setPublicationstatus(publicationstatus);
        setEtage(etage);
        setMapLocation(mapLocation);
        setShowingDate(showingDate);
        setWbs(wbs);
        setWbsDetails(wbsDetails);
        setNumberOfRooms(numberOfRooms);
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
            setEtage(value.getEtage());
            setMapLocation(value.getMapLocation());
            setShowingDate(value.getShowingDate());
            setWbs(value.getWbs());
            setWbsDetails(value.getWbsDetails());
            setNumberOfRooms(value.getNumberOfRooms());
        }
    }
}
