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

    private String  id;
    private String  city;
    private String  bundesland;
    private Short   minTenants;
    private Short   maxTenants;
    private String  description;
    private Boolean petsAllowed;
    private String  publicationstatus;
    private String  etage;
    private String  mapLocation;
    private String  showingDate;
    private Boolean wbs;
    private String  wbsDetails;
    private Short   numberOfRooms;
    private String  postCode;

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
        this.etage = value.etage;
        this.mapLocation = value.mapLocation;
        this.showingDate = value.showingDate;
        this.wbs = value.wbs;
        this.wbsDetails = value.wbsDetails;
        this.numberOfRooms = value.numberOfRooms;
        this.postCode = value.postCode;
    }

    public Apartment(
        String  id,
        String  city,
        String  bundesland,
        Short   minTenants,
        Short   maxTenants,
        String  description,
        Boolean petsAllowed,
        String  publicationstatus,
        String  etage,
        String  mapLocation,
        String  showingDate,
        Boolean wbs,
        String  wbsDetails,
        Short   numberOfRooms,
        String  postCode
    ) {
        this.id = id;
        this.city = city;
        this.bundesland = bundesland;
        this.minTenants = minTenants;
        this.maxTenants = maxTenants;
        this.description = description;
        this.petsAllowed = petsAllowed;
        this.publicationstatus = publicationstatus;
        this.etage = etage;
        this.mapLocation = mapLocation;
        this.showingDate = showingDate;
        this.wbs = wbs;
        this.wbsDetails = wbsDetails;
        this.numberOfRooms = numberOfRooms;
        this.postCode = postCode;
    }

    /**
     * Getter for <code>main.apartment.id</code>.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Setter for <code>main.apartment.id</code>.
     */
    public void setId(String id) {
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

    /**
     * Getter for <code>main.apartment.etage</code>.
     */
    public String getEtage() {
        return this.etage;
    }

    /**
     * Setter for <code>main.apartment.etage</code>.
     */
    public void setEtage(String etage) {
        this.etage = etage;
    }

    /**
     * Getter for <code>main.apartment.map_location</code>.
     */
    public String getMapLocation() {
        return this.mapLocation;
    }

    /**
     * Setter for <code>main.apartment.map_location</code>.
     */
    public void setMapLocation(String mapLocation) {
        this.mapLocation = mapLocation;
    }

    /**
     * Getter for <code>main.apartment.showing_date</code>.
     */
    public String getShowingDate() {
        return this.showingDate;
    }

    /**
     * Setter for <code>main.apartment.showing_date</code>.
     */
    public void setShowingDate(String showingDate) {
        this.showingDate = showingDate;
    }

    /**
     * Getter for <code>main.apartment.wbs</code>.
     */
    public Boolean getWbs() {
        return this.wbs;
    }

    /**
     * Setter for <code>main.apartment.wbs</code>.
     */
    public void setWbs(Boolean wbs) {
        this.wbs = wbs;
    }

    /**
     * Getter for <code>main.apartment.wbs_details</code>.
     */
    public String getWbsDetails() {
        return this.wbsDetails;
    }

    /**
     * Setter for <code>main.apartment.wbs_details</code>.
     */
    public void setWbsDetails(String wbsDetails) {
        this.wbsDetails = wbsDetails;
    }

    /**
     * Getter for <code>main.apartment.number_of_rooms</code>.
     */
    public Short getNumberOfRooms() {
        return this.numberOfRooms;
    }

    /**
     * Setter for <code>main.apartment.number_of_rooms</code>.
     */
    public void setNumberOfRooms(Short numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    /**
     * Getter for <code>main.apartment.post_code</code>.
     */
    public String getPostCode() {
        return this.postCode;
    }

    /**
     * Setter for <code>main.apartment.post_code</code>.
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
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
        sb.append(", ").append(etage);
        sb.append(", ").append(mapLocation);
        sb.append(", ").append(showingDate);
        sb.append(", ").append(wbs);
        sb.append(", ").append(wbsDetails);
        sb.append(", ").append(numberOfRooms);
        sb.append(", ").append(postCode);

        sb.append(")");
        return sb.toString();
    }
}
