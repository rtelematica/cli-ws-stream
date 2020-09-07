package com.walkoding.cliwsstream.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StepIdsFrontExtractorDTO {


    private CustomerName customerName;

    private Address address;

    private CustomerData customerData;

    private StepMetadataDTO metadata;

    @Data
    public static class CustomerName {


        private String name;

        private String lastName;

        private String motherLastName;
    }

    @Data
    public static class Address {

        private String externalNumber;

        private String internalNumber;

        private String neighborhood;

        private String postalCode;

        private String municipality;

        private String city;

        private String state;

        private String country;
    }

    @Data
    public static class CustomerData {

        private LocalDate birthDate;

        private String placeBirth;

        private String gender;

        private String curp;

        private String identificationNumber;
    }
}
