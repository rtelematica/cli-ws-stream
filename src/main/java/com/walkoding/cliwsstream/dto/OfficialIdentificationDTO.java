package com.walkoding.cliwsstream.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.mx.banbajio.domain.OfficialIdentification} entity.
 */
public class OfficialIdentificationDTO implements Serializable {
    
    private Long id;

    private String userName;

    private String userLastName;

    private String mLastName;

    private Integer age;

    private String curp;

    private String address;

    private LocalDate birthDate;

    private String gender;

    private String identificationNumber;

    private String state;


    private Long officialIdentificationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getOfficialIdentificationId() {
        return officialIdentificationId;
    }

    public void setOfficialIdentificationId(Long catOfficialIdentificationId) {
        this.officialIdentificationId = catOfficialIdentificationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfficialIdentificationDTO)) {
            return false;
        }

        return id != null && id.equals(((OfficialIdentificationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfficialIdentificationDTO{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", userLastName='" + getUserLastName() + "'" +
            ", mLastName='" + getmLastName() + "'" +
            ", age=" + getAge() +
            ", curp='" + getCurp() + "'" +
            ", address='" + getAddress() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", gender='" + getGender() + "'" +
            ", identificationNumber='" + getIdentificationNumber() + "'" +
            ", state='" + getState() + "'" +
            ", officialIdentificationId=" + getOfficialIdentificationId() +
            "}";
    }
}
