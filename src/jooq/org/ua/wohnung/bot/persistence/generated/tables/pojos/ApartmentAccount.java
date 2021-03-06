/*
 * This file is generated by jOOQ.
 */
package org.ua.wohnung.bot.persistence.generated.tables.pojos;


import java.io.Serializable;
import java.time.OffsetDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ApartmentAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long           accountId;
    private String         apartmentId;
    private OffsetDateTime applyTs;

    public ApartmentAccount() {}

    public ApartmentAccount(ApartmentAccount value) {
        this.accountId = value.accountId;
        this.apartmentId = value.apartmentId;
        this.applyTs = value.applyTs;
    }

    public ApartmentAccount(
        Long           accountId,
        String         apartmentId,
        OffsetDateTime applyTs
    ) {
        this.accountId = accountId;
        this.apartmentId = apartmentId;
        this.applyTs = applyTs;
    }

    /**
     * Getter for <code>main.apartment_account.account_id</code>.
     */
    public Long getAccountId() {
        return this.accountId;
    }

    /**
     * Setter for <code>main.apartment_account.account_id</code>.
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * Getter for <code>main.apartment_account.apartment_id</code>.
     */
    public String getApartmentId() {
        return this.apartmentId;
    }

    /**
     * Setter for <code>main.apartment_account.apartment_id</code>.
     */
    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    /**
     * Getter for <code>main.apartment_account.apply_ts</code>.
     */
    public OffsetDateTime getApplyTs() {
        return this.applyTs;
    }

    /**
     * Setter for <code>main.apartment_account.apply_ts</code>.
     */
    public void setApplyTs(OffsetDateTime applyTs) {
        this.applyTs = applyTs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ApartmentAccount (");

        sb.append(accountId);
        sb.append(", ").append(apartmentId);
        sb.append(", ").append(applyTs);

        sb.append(")");
        return sb.toString();
    }
}
