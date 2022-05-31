/*
 * This file is generated by jOOQ.
 */
package org.ua.wohnung.bot.persistence.generated.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String  username;
    private final String  phone;
    private final Short   numberOfTenants;
    private final Boolean pets;
    private final String  bundesLand;

    public UserDetails(UserDetails value) {
        this.username = value.username;
        this.phone = value.phone;
        this.numberOfTenants = value.numberOfTenants;
        this.pets = value.pets;
        this.bundesLand = value.bundesLand;
    }

    public UserDetails(
        String  username,
        String  phone,
        Short   numberOfTenants,
        Boolean pets,
        String  bundesLand
    ) {
        this.username = username;
        this.phone = phone;
        this.numberOfTenants = numberOfTenants;
        this.pets = pets;
        this.bundesLand = bundesLand;
    }

    /**
     * Getter for <code>main.user_details.username</code>.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Getter for <code>main.user_details.phone</code>.
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Getter for <code>main.user_details.number_of_tenants</code>.
     */
    public Short getNumberOfTenants() {
        return this.numberOfTenants;
    }

    /**
     * Getter for <code>main.user_details.pets</code>.
     */
    public Boolean getPets() {
        return this.pets;
    }

    /**
     * Getter for <code>main.user_details.bundes_land</code>.
     */
    public String getBundesLand() {
        return this.bundesLand;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserDetails (");

        sb.append(username);
        sb.append(", ").append(phone);
        sb.append(", ").append(numberOfTenants);
        sb.append(", ").append(pets);
        sb.append(", ").append(bundesLand);

        sb.append(")");
        return sb.toString();
    }
}
