package com.cumulocity.pkiintegration.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Registration {

	@JsonProperty("regid")
	private String regId;
	@JsonProperty("fqdn")
	private String fqdn;
	@JsonProperty("officer")
	private String officer;

}
