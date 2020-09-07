package com.walkoding.cliwsstream.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mx.banbajio.domain.ProofAddress} entity.
 */
public class ProofAddressDTO implements Serializable {

    private Long id;

    private String streetName;

    private String postalCode;

    private String externalNumber;

    private String internalNumber;

    private String neighborhood;

    private String municipally;

    private String city;

    private String state;

    private String country;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getExternalNumber() {
        return externalNumber;
    }

    public void setExternalNumber(String externalNumber) {
        this.externalNumber = externalNumber;
    }

    public String getInternalNumber() {
        return internalNumber;
    }

    public void setInternalNumber(String internalNumber) {
        this.internalNumber = internalNumber;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getMunicipally() {
        return municipally;
    }

    public void setMunicipally(String municipally) {
        this.municipally = municipally;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProofAddressDTO)) {
            return false;
        }

        return id != null && id.equals(((ProofAddressDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProofAddressDTO{" +
                "id=" + getId() +
                ", streetName='" + getStreetName() + "'" +
                ", postalCode='" + getPostalCode() + "'" +
                ", externalNumber='" + getExternalNumber() + "'" +
                ", internalNumber='" + getInternalNumber() + "'" +
                ", neighborhood='" + getNeighborhood() + "'" +
                ", municipally='" + getMunicipally() + "'" +
                ", city='" + getCity() + "'" +
                ", state='" + getState() + "'" +
                ", country='" + getCountry() + "'" +
                "}";
    }
}
