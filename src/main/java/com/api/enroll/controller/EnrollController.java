package com.api.enroll.controller;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.enroll.exception.ValidationException;
import com.api.enroll.model.Enrolee;
import com.api.enroll.request.model.DependentRequest;
import com.api.enroll.request.model.EnroleeRequest;
import com.api.enroll.service.EnrollService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class EnrollController {

	@Autowired
	private EnrollService enrollService;

	@GetMapping(value = "/getEnrolee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value = "GET ENROLEE", position = 1)
	public ResponseEntity<Enrolee> getEnrolee(@NotNull @PathVariable Long id) {
		log.info("Start: registerAttendee");
		return ResponseEntity.ok(enrollService.getEnrolee(id));
	}

	@PostMapping(value = "/registerEnrolee", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value = "ADD ENROLEE", position = 2)
	public ResponseEntity<Enrolee> registerEnrolee(@Valid @RequestBody EnroleeRequest enroleeRequest) {
		log.info("Start: registerAttendee");
		return ResponseEntity.ok(enrollService.registerEnrolee(enroleeRequest));
	}

	@PutMapping(value = "/editEnrolee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value = "EDIT ENROLEE", position = 3)
	public ResponseEntity<Enrolee> editEnrolee(@NotNull @PathVariable Long id,
			@Valid @RequestBody EnroleeRequest enroleeRequest) {
		log.info("Start: registerAttendee");
		return ResponseEntity.ok(enrollService.editEnrolee(id, enroleeRequest));
	}

	@DeleteMapping(value = "/deleteEnrolee/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	@ApiOperation(value = "DELETE ENROLEE", position = 4)
	public ResponseEntity<String> deleteEnrolee(@NotNull @PathVariable Long id) {
		log.info("Start: registerAttendee");
		enrollService.deleteEnrolee(id);
		return ResponseEntity.ok("SUCCESS");
	}

	@ApiOperation(value = "ADD DEPENDENT", position = 5)
	@PostMapping(value = "/addDependent", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Enrolee> addDependent(@Valid @RequestBody DependentRequest dependentRequest) {
		log.info("Start: addDependent");
		return ResponseEntity.ok(enrollService.addDependent(dependentRequest));
	}

	@ApiOperation(value = "EDIT DEPENDENT", position = 6)
	@PutMapping(value = "/editDependent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Enrolee> editDependent(@Valid @RequestBody DependentRequest dependentRequest) {
		log.info("Start: editDependent");
		if (Objects.isNull(dependentRequest.getDependentId())) {
			throw new ValidationException("Dependent Id should be provided");
		}
		
		return ResponseEntity.ok(enrollService.editDependent(dependentRequest));
	}

	@ApiOperation(value = "DELETE DEPENDENT", position = 7)
	@DeleteMapping(value = "/deleteDependent/enroleeId/{enroleeId}/dependentId/{dependentId}", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public ResponseEntity<String> deleteDependent(@NotNull @PathVariable Long enroleeId,
			@NotNull @PathVariable Long dependentId) {
		log.info("Start: deleteDependent");
		enrollService.deleteDependent(enroleeId, dependentId);
		return ResponseEntity.ok("SUCCESS");
	}
}
