package com.api.enroll.request.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "enroleeId","name","dob"})
public class DependentRequest {

	@NotNull
	@JsonProperty("enroleeId")
	private Long enroleeId;
	@JsonProperty("dependentId")
	private Long dependentId;
	@NotNull
	@JsonProperty("dependentName")
	private String dependentName;
	@NotBlank
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty("dependentDob")
	@ApiModelProperty(dataType = "java.sql.Date")
	private Date dependentDob;
	

}
