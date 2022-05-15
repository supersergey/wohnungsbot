/*
 * This file is generated by jOOQ.
 */
package org.ua.wohnung.bot.persistence.generated.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;
import org.ua.wohnung.bot.persistence.generated.enums.Role;
import org.ua.wohnung.bot.persistence.generated.tables.Account;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AccountRecord extends UpdatableRecordImpl<AccountRecord> implements Record2<String, Role> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>main.account.login</code>.
     */
    public void setLogin(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>main.account.login</code>.
     */
    public String getLogin() {
        return (String) get(0);
    }

    /**
     * Setter for <code>main.account.role</code>.
     */
    public void setRole(Role value) {
        set(1, value);
    }

    /**
     * Getter for <code>main.account.role</code>.
     */
    public Role getRole() {
        return (Role) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, Role> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<String, Role> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Account.ACCOUNT.LOGIN;
    }

    @Override
    public Field<Role> field2() {
        return Account.ACCOUNT.ROLE;
    }

    @Override
    public String component1() {
        return getLogin();
    }

    @Override
    public Role component2() {
        return getRole();
    }

    @Override
    public String value1() {
        return getLogin();
    }

    @Override
    public Role value2() {
        return getRole();
    }

    @Override
    public AccountRecord value1(String value) {
        setLogin(value);
        return this;
    }

    @Override
    public AccountRecord value2(Role value) {
        setRole(value);
        return this;
    }

    @Override
    public AccountRecord values(String value1, Role value2) {
        value1(value1);
        value2(value2);
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
    public AccountRecord(String login, Role role) {
        super(Account.ACCOUNT);

        setLogin(login);
        setRole(role);
    }

    /**
     * Create a detached, initialised AccountRecord
     */
    public AccountRecord(org.ua.wohnung.bot.persistence.generated.tables.pojos.Account value) {
        super(Account.ACCOUNT);

        if (value != null) {
            setLogin(value.getLogin());
            setRole(value.getRole());
        }
    }
}
