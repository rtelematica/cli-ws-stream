package com.walkoding.cliwsstream.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StepMetadataDTO {

    public StepMetadataDTO(Boolean success) {
        this.success = success;
    }

    private Boolean success;

    private Integer status;

    private String message;
}
