package com.api.enroll.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ENROLEE")
public class Enrolee extends CommonFields{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long enroleeId;
	@NotBlank
	private String name;
	@NotBlank
	private Boolean status;
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dob;
	@Column
	private String phoneNumber;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "conferenceId")
	private Set<Dependent> dependents;
}
