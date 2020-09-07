package com.walkoding.cliwsstream.dto;

import lombok.Data;

@Data
public class StepAccountTypeDTO {


    private String flowId;

    private String type;

    private StepMetadataDTO metadata;
}
