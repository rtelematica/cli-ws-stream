package com.walkoding.cliwsstream.dto;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.mx.banbajio.domain.Document} entity.
 */
public class DocumentDTO implements Serializable {
    
    private Long id;

    private String description;

    private String objectStoreId;

    @DateTimeFormat(iso = ISO.DATE) 
    private LocalDate registrationDate;

    private Long accountOpeningId;

    private Long catDocumentId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObjectStoreId() {
        return objectStoreId;
    }

    public void setObjectStoreId(String objectStoreId) {
        this.objectStoreId = objectStoreId;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Long getAccountOpeningId() {
        return accountOpeningId;
    }

    public void setAccountOpeningId(Long accountOpeningId) {
        this.accountOpeningId = accountOpeningId;
    }

    public Long getCatDocumentId() {
        return catDocumentId;
    }

    public void setCatDocumentId(Long catDocumentId) {
        this.catDocumentId = catDocumentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentDTO)) {
            return false;
        }

        return id != null && id.equals(((DocumentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", objectStoreId='" + getObjectStoreId() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            ", accountOpeningId=" + getAccountOpeningId() +
            ", catDocumentId=" + getCatDocumentId() +
            "}";
    }
}
