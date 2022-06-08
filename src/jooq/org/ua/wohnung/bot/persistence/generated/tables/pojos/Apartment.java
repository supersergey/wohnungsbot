/*
 * This file is generated by jOOQ.
 */
package org.ua.wohnung.bot.persistence.generated.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Apartment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long    id;
    private String  city;
    private String  bundesland;
    private Short   minTenants;
    private Short   maxTenants;
    private String  description;
    private Boolean petsAllowed;
    private String  publicationstatus;

    public Apartment() {}

    public Apartment(Apartment value) {
        this.id = value.id;
        this.city = value.city;
        this.bundesland = value.bundesland;
        this.minTenants = value.minTenants;
        this.maxTenants = value.maxTenants;
        this.description = value.description;
        this.petsAllowed = value.petsAllowed;
        this.publicationstatus = value.publicationstatus;
    }

    public Apartment(
        Long    id,
        String  city,
        String  bundesland,
        Short   minTenants,
        Short   maxTenants,
        String  description,
        Boolean petsAllowed,
        String  publicationstatus
    ) {
        this.id = id;
        this.city = city;
        this.bundesland = bundesland;
        this.minTenants = minTenants;
        this.maxTenants = maxTenants;
        this.description = description;
        this.petsAllowed = petsAllowed;
        this.publicationstatus = publicationstatus;
    }

    /**
     * Getter for <code>main.apartment.id</code>.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>main.apartment.id</code>.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>main.apartment.city</code>.
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Setter for <code>main.apartment.city</code>.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Getter for <code>main.apartment.bundesland</code>.
     */
    public String getBundesland() {
        return this.bundesland;
    }

    /**
     * Setter for <code>main.apartment.bundesland</code>.
     */
    public void setBundesland(String bundesland) {
        this.bundesland = bundesland;
    }

    /**
     * Getter for <code>main.apartment.min_tenants</code>.
     */
    public Short getMinTenants() {
        return this.minTenants;
    }

    /**
     * Setter for <code>main.apartment.min_tenants</code>.
     */
    public void setMinTenants(Short minTenants) {
        this.minTenants = minTenants;
    }

    /**
     * Getter for <code>main.apartment.max_tenants</code>.
     */
    public Short getMaxTenants() {
        return this.maxTenants;
    }

    /**
     * Setter for <code>main.apartment.max_tenants</code>.
     */
    public void setMaxTenants(Short maxTenants) {
        this.maxTenants = maxTenants;
    }

    /**
     * Getter for <code>main.apartment.description</code>.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Setter for <code>main.apartment.description</code>.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for <code>main.apartment.pets_allowed</code>.
     */
    public Boolean getPetsAllowed() {
        return this.petsAllowed;
    }

    /**
     * Setter for <code>main.apartment.pets_allowed</code>.
     */
    public void setPetsAllowed(Boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    /**
     * Getter for <code>main.apartment.publicationstatus</code>.
     */
    public String getPublicationstatus() {
        return this.publicationstatus;
    }

    /**
     * Setter for <code>main.apartment.publicationstatus</code>.
     */
    public void setPublicationstatus(String publicationstatus) {
        this.publicationstatus = publicationstatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Apartment (");

        sb.append(id);
        sb.append(", ").append(city);
        sb.append(", ").append(bundesland);
        sb.append(", ").append(minTenants);
        sb.append(", ").append(maxTenants);
        sb.append(", ").append(description);
        sb.append(", ").append(petsAllowed);
        sb.append(", ").append(publicationstatus);

        sb.append(")");
        return sb.toString();
    }
}
