/*
 * This file is generated by jOOQ.
 */
package org.ua.wohnung.bot.persistence.generated.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;
import org.ua.wohnung.bot.persistence.generated.tables.UserDetails;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserDetailsRecord extends UpdatableRecordImpl<UserDetailsRecord> implements Record6<Long, String, String, Short, Boolean, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>main.user_details.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>main.user_details.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>main.user_details.first_last_name</code>.
     */
    public void setFirstLastName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>main.user_details.first_last_name</code>.
     */
    public String getFirstLastName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>main.user_details.phone</code>.
     */
    public void setPhone(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>main.user_details.phone</code>.
     */
    public String getPhone() {
        return (String) get(2);
    }

    /**
     * Setter for <code>main.user_details.number_of_tenants</code>.
     */
    public void setNumberOfTenants(Short value) {
        set(3, value);
    }

    /**
     * Getter for <code>main.user_details.number_of_tenants</code>.
     */
    public Short getNumberOfTenants() {
        return (Short) get(3);
    }

    /**
     * Setter for <code>main.user_details.pets</code>.
     */
    public void setPets(Boolean value) {
        set(4, value);
    }

    /**
     * Getter for <code>main.user_details.pets</code>.
     */
    public Boolean getPets() {
        return (Boolean) get(4);
    }

    /**
     * Setter for <code>main.user_details.bundesland</code>.
     */
    public void setBundesland(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>main.user_details.bundesland</code>.
     */
    public String getBundesland() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Long, String, String, Short, Boolean, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Long, String, String, Short, Boolean, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return UserDetails.USER_DETAILS.ID;
    }

    @Override
    public Field<String> field2() {
        return UserDetails.USER_DETAILS.FIRST_LAST_NAME;
    }

    @Override
    public Field<String> field3() {
        return UserDetails.USER_DETAILS.PHONE;
    }

    @Override
    public Field<Short> field4() {
        return UserDetails.USER_DETAILS.NUMBER_OF_TENANTS;
    }

    @Override
    public Field<Boolean> field5() {
        return UserDetails.USER_DETAILS.PETS;
    }

    @Override
    public Field<String> field6() {
        return UserDetails.USER_DETAILS.BUNDESLAND;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getFirstLastName();
    }

    @Override
    public String component3() {
        return getPhone();
    }

    @Override
    public Short component4() {
        return getNumberOfTenants();
    }

    @Override
    public Boolean component5() {
        return getPets();
    }

    @Override
    public String component6() {
        return getBundesland();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getFirstLastName();
    }

    @Override
    public String value3() {
        return getPhone();
    }

    @Override
    public Short value4() {
        return getNumberOfTenants();
    }

    @Override
    public Boolean value5() {
        return getPets();
    }

    @Override
    public String value6() {
        return getBundesland();
    }

    @Override
    public UserDetailsRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public UserDetailsRecord value2(String value) {
        setFirstLastName(value);
        return this;
    }

    @Override
    public UserDetailsRecord value3(String value) {
        setPhone(value);
        return this;
    }

    @Override
    public UserDetailsRecord value4(Short value) {
        setNumberOfTenants(value);
        return this;
    }

    @Override
    public UserDetailsRecord value5(Boolean value) {
        setPets(value);
        return this;
    }

    @Override
    public UserDetailsRecord value6(String value) {
        setBundesland(value);
        return this;
    }

    @Override
    public UserDetailsRecord values(Long value1, String value2, String value3, Short value4, Boolean value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserDetailsRecord
     */
    public UserDetailsRecord() {
        super(UserDetails.USER_DETAILS);
    }

    /**
     * Create a detached, initialised UserDetailsRecord
     */
    public UserDetailsRecord(Long id, String firstLastName, String phone, Short numberOfTenants, Boolean pets, String bundesland) {
        super(UserDetails.USER_DETAILS);

        setId(id);
        setFirstLastName(firstLastName);
        setPhone(phone);
        setNumberOfTenants(numberOfTenants);
        setPets(pets);
        setBundesland(bundesland);
    }

    /**
     * Create a detached, initialised UserDetailsRecord
     */
    public UserDetailsRecord(org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails value) {
        super(UserDetails.USER_DETAILS);

        if (value != null) {
            setId(value.getId());
            setFirstLastName(value.getFirstLastName());
            setPhone(value.getPhone());
            setNumberOfTenants(value.getNumberOfTenants());
            setPets(value.getPets());
            setBundesland(value.getBundesland());
        }
    }
}
