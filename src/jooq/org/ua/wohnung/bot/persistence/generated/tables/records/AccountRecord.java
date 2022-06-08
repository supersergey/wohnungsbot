/*
 * This file is generated by jOOQ.
 */
package org.ua.wohnung.bot.persistence.generated.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;
import org.ua.wohnung.bot.persistence.generated.enums.Role;
import org.ua.wohnung.bot.persistence.generated.tables.Account;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AccountRecord extends UpdatableRecordImpl<AccountRecord> implements Record4<String, String, String, Role> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>main.account.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>main.account.id</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>main.account.chat_id</code>.
     */
    public void setChatId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>main.account.chat_id</code>.
     */
    public String getChatId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>main.account.username</code>.
     */
    public void setUsername(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>main.account.username</code>.
     */
    public String getUsername() {
        return (String) get(2);
    }

    /**
     * Setter for <code>main.account.role</code>.
     */
    public void setRole(Role value) {
        set(3, value);
    }

    /**
     * Getter for <code>main.account.role</code>.
     */
    public Role getRole() {
        return (Role) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<String, String, String, Role> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<String, String, String, Role> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Account.ACCOUNT.ID;
    }

    @Override
    public Field<String> field2() {
        return Account.ACCOUNT.CHAT_ID;
    }

    @Override
    public Field<String> field3() {
        return Account.ACCOUNT.USERNAME;
    }

    @Override
    public Field<Role> field4() {
        return Account.ACCOUNT.ROLE;
    }

    @Override
    public String component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getChatId();
    }

    @Override
    public String component3() {
        return getUsername();
    }

    @Override
    public Role component4() {
        return getRole();
    }

    @Override
    public String value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getChatId();
    }

    @Override
    public String value3() {
        return getUsername();
    }

    @Override
    public Role value4() {
        return getRole();
    }

    @Override
    public AccountRecord value1(String value) {
        setId(value);
        return this;
    }

    @Override
    public AccountRecord value2(String value) {
        setChatId(value);
        return this;
    }

    @Override
    public AccountRecord value3(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public AccountRecord value4(Role value) {
        setRole(value);
        return this;
    }

    @Override
    public AccountRecord values(String value1, String value2, String value3, Role value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AccountRecord
     */
    public AccountRecord() {
        super(Account.ACCOUNT);
    }

    /**
     * Create a detached, initialised AccountRecord
     */
    public AccountRecord(String id, String chatId, String username, Role role) {
        super(Account.ACCOUNT);

        setId(id);
        setChatId(chatId);
        setUsername(username);
        setRole(role);
    }

    /**
     * Create a detached, initialised AccountRecord
     */
    public AccountRecord(org.ua.wohnung.bot.persistence.generated.tables.pojos.Account value) {
        super(Account.ACCOUNT);

        if (value != null) {
            setId(value.getId());
            setChatId(value.getChatId());
            setUsername(value.getUsername());
            setRole(value.getRole());
        }
    }
}
