package com.walkoding.cliwsstream.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A DTO for the {@link com.mx.banbajio.domain.AccountOpening} entity.
 */
@Data
public class MetadataDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8394974180103515002L;

	private String currentStep;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private Boolean success;
}
