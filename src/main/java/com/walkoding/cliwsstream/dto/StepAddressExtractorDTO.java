package com.walkoding.cliwsstream.dto;

import lombok.Data;

@Data
public class StepAddressExtractorDTO {


    private String streetName;

    private String externalNumber;

    private String internalNumber;

    private String neighborhood;

    private String postalCode;

    private String municipally;

    private String city;

    private String state;

    private String country;

    private StepMetadataDTO metadata;
}
