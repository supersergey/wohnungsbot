/*
 * This file is generated by jOOQ.
 */
package org.ua.wohnung.bot.persistence.generated.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;
import org.ua.wohnung.bot.persistence.generated.tables.UserDetails;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserDetailsRecord extends UpdatableRecordImpl<UserDetailsRecord> implements Record11<Long, String, String, Short, Boolean, String, String, Boolean, String, String, String> {

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

    /**
     * Setter for <code>main.user_details.family_members</code>.
     */
    public void setFamilyMembers(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>main.user_details.family_members</code>.
     */
    public String getFamilyMembers() {
        return (String) get(6);
    }

    /**
     * Setter for <code>main.user_details.ready_to_move</code>.
     */
    public void setReadyToMove(Boolean value) {
        set(7, value);
    }

    /**
     * Getter for <code>main.user_details.ready_to_move</code>.
     */
    public Boolean getReadyToMove() {
        return (Boolean) get(7);
    }

    /**
     * Setter for <code>main.user_details.foreign_languages</code>.
     */
    public void setForeignLanguages(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>main.user_details.foreign_languages</code>.
     */
    public String getForeignLanguages() {
        return (String) get(8);
    }

    /**
     * Setter for <code>main.user_details.allergies</code>.
     */
    public void setAllergies(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>main.user_details.allergies</code>.
     */
    public String getAllergies() {
        return (String) get(9);
    }

    /**
     * Setter for <code>main.user_details.district</code>.
     */
    public void setDistrict(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>main.user_details.district</code>.
     */
    public String getDistrict() {
        return (String) get(10);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record11 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row11<Long, String, String, Short, Boolean, String, String, Boolean, String, String, String> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    @Override
    public Row11<Long, String, String, Short, Boolean, String, String, Boolean, String, String, String> valuesRow() {
        return (Row11) super.valuesRow();
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
    public Field<String> field7() {
        return UserDetails.USER_DETAILS.FAMILY_MEMBERS;
    }

    @Override
    public Field<Boolean> field8() {
        return UserDetails.USER_DETAILS.READY_TO_MOVE;
    }

    @Override
    public Field<String> field9() {
        return UserDetails.USER_DETAILS.FOREIGN_LANGUAGES;
    }

    @Override
    public Field<String> field10() {
        return UserDetails.USER_DETAILS.ALLERGIES;
    }

    @Override
    public Field<String> field11() {
        return UserDetails.USER_DETAILS.DISTRICT;
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
    public String component7() {
        return getFamilyMembers();
    }

    @Override
    public Boolean component8() {
        return getReadyToMove();
    }

    @Override
    public String component9() {
        return getForeignLanguages();
    }

    @Override
    public String component10() {
        return getAllergies();
    }

    @Override
    public String component11() {
        return getDistrict();
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
    public String value7() {
        return getFamilyMembers();
    }

    @Override
    public Boolean value8() {
        return getReadyToMove();
    }

    @Override
    public String value9() {
        return getForeignLanguages();
    }

    @Override
    public String value10() {
        return getAllergies();
    }

    @Override
    public String value11() {
        return getDistrict();
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
    public UserDetailsRecord value7(String value) {
        setFamilyMembers(value);
        return this;
    }

    @Override
    public UserDetailsRecord value8(Boolean value) {
        setReadyToMove(value);
        return this;
    }

    @Override
    public UserDetailsRecord value9(String value) {
        setForeignLanguages(value);
        return this;
    }

    @Override
    public UserDetailsRecord value10(String value) {
        setAllergies(value);
        return this;
    }

    @Override
    public UserDetailsRecord value11(String value) {
        setDistrict(value);
        return this;
    }

    @Override
    public UserDetailsRecord values(Long value1, String value2, String value3, Short value4, Boolean value5, String value6, String value7, Boolean value8, String value9, String value10, String value11) {
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
    public UserDetailsRecord(Long id, String firstLastName, String phone, Short numberOfTenants, Boolean pets, String bundesland, String familyMembers, Boolean readyToMove, String foreignLanguages, String allergies, String district) {
        super(UserDetails.USER_DETAILS);

        setId(id);
        setFirstLastName(firstLastName);
        setPhone(phone);
        setNumberOfTenants(numberOfTenants);
        setPets(pets);
        setBundesland(bundesland);
        setFamilyMembers(familyMembers);
        setReadyToMove(readyToMove);
        setForeignLanguages(foreignLanguages);
        setAllergies(allergies);
        setDistrict(district);
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
            setFamilyMembers(value.getFamilyMembers());
            setReadyToMove(value.getReadyToMove());
            setForeignLanguages(value.getForeignLanguages());
            setAllergies(value.getAllergies());
            setDistrict(value.getDistrict());
        }
    }
}
