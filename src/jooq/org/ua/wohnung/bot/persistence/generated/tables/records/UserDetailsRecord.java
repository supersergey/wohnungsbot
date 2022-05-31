/*
 * This file is generated by jOOQ.
 */
package org.ua.wohnung.bot.persistence.generated.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;
import org.ua.wohnung.bot.persistence.generated.tables.UserDetails;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserDetailsRecord extends UpdatableRecordImpl<UserDetailsRecord> implements Record5<String, String, Short, Boolean, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>main.user_details.username</code>.
     */
    public void setUsername(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>main.user_details.username</code>.
     */
    public String getUsername() {
        return (String) get(0);
    }

    /**
     * Setter for <code>main.user_details.phone</code>.
     */
    public void setPhone(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>main.user_details.phone</code>.
     */
    public String getPhone() {
        return (String) get(1);
    }

    /**
     * Setter for <code>main.user_details.number_of_tenants</code>.
     */
    public void setNumberOfTenants(Short value) {
        set(2, value);
    }

    /**
     * Getter for <code>main.user_details.number_of_tenants</code>.
     */
    public Short getNumberOfTenants() {
        return (Short) get(2);
    }

    /**
     * Setter for <code>main.user_details.pets</code>.
     */
    public void setPets(Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>main.user_details.pets</code>.
     */
    public Boolean getPets() {
        return (Boolean) get(3);
    }

    /**
     * Setter for <code>main.user_details.bundes_land</code>.
     */
    public void setBundesLand(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>main.user_details.bundes_land</code>.
     */
    public String getBundesLand() {
        return (String) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<String, String, Short, Boolean, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<String, String, Short, Boolean, String> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return UserDetails.USER_DETAILS.USERNAME;
    }

    @Override
    public Field<String> field2() {
        return UserDetails.USER_DETAILS.PHONE;
    }

    @Override
    public Field<Short> field3() {
        return UserDetails.USER_DETAILS.NUMBER_OF_TENANTS;
    }

    @Override
    public Field<Boolean> field4() {
        return UserDetails.USER_DETAILS.PETS;
    }

    @Override
    public Field<String> field5() {
        return UserDetails.USER_DETAILS.BUNDES_LAND;
    }

    @Override
    public String component1() {
        return getUsername();
    }

    @Override
    public String component2() {
        return getPhone();
    }

    @Override
    public Short component3() {
        return getNumberOfTenants();
    }

    @Override
    public Boolean component4() {
        return getPets();
    }

    @Override
    public String component5() {
        return getBundesLand();
    }

    @Override
    public String value1() {
        return getUsername();
    }

    @Override
    public String value2() {
        return getPhone();
    }

    @Override
    public Short value3() {
        return getNumberOfTenants();
    }

    @Override
    public Boolean value4() {
        return getPets();
    }

    @Override
    public String value5() {
        return getBundesLand();
    }

    @Override
    public UserDetailsRecord value1(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public UserDetailsRecord value2(String value) {
        setPhone(value);
        return this;
    }

    @Override
    public UserDetailsRecord value3(Short value) {
        setNumberOfTenants(value);
        return this;
    }

    @Override
    public UserDetailsRecord value4(Boolean value) {
        setPets(value);
        return this;
    }

    @Override
    public UserDetailsRecord value5(String value) {
        setBundesLand(value);
        return this;
    }

    @Override
    public UserDetailsRecord values(String value1, String value2, Short value3, Boolean value4, String value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
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
    public UserDetailsRecord(String username, String phone, Short numberOfTenants, Boolean pets, String bundesLand) {
        super(UserDetails.USER_DETAILS);

        setUsername(username);
        setPhone(phone);
        setNumberOfTenants(numberOfTenants);
        setPets(pets);
        setBundesLand(bundesLand);
    }

    /**
     * Create a detached, initialised UserDetailsRecord
     */
    public UserDetailsRecord(org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails value) {
        super(UserDetails.USER_DETAILS);

        if (value != null) {
            setUsername(value.getUsername());
            setPhone(value.getPhone());
            setNumberOfTenants(value.getNumberOfTenants());
            setPets(value.getPets());
            setBundesLand(value.getBundesLand());
        }
    }
}
