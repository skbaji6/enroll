package com.api.enroll.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.api.enroll.exception.ResourceNotFoundException;
import com.api.enroll.model.Dependent;
import com.api.enroll.model.Enrolee;
import com.api.enroll.repository.EnroleeRepository;
import com.api.enroll.request.model.DependentRequest;
import com.api.enroll.request.model.EnroleeRequest;

@Service
public class EnrollService {

	@Autowired
	private EnroleeRepository enroleeRepository;

	public Enrolee registerEnrolee(EnroleeRequest enroleeRequest) {
		Enrolee enrolee = new Enrolee();
		BeanUtils.copyProperties(enroleeRequest, enrolee);
		enrolee.setCreatedTimestamp(new Date());
		enrolee.setUpdatedTimestamp(new Date());
		return enroleeRepository.save(enrolee);
	}

	public Enrolee getEnrolee(Long id) {
		return enroleeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Enrolee", "id", id));
	}

	public Enrolee editEnrolee(Long id, EnroleeRequest enroleeRequest) {
		Enrolee enrolee = enroleeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Enrolee", "id", id));
		BeanUtils.copyProperties(enroleeRequest, enrolee);
		enrolee.setUpdatedTimestamp(new Date());
		return enroleeRepository.save(enrolee);
	}

	public void deleteEnrolee(Long id) {
		enroleeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Enrolee", "id", id));
		enroleeRepository.deleteById(id);
	}

	public Enrolee addDependent(@Valid DependentRequest dependentRequest) {
		Enrolee enrolee = enroleeRepository.findById(dependentRequest.getEnroleeId())
				.orElseThrow(() -> new ResourceNotFoundException("Enrolee", "id", dependentRequest.getEnroleeId()));
		Dependent dependent = new Dependent();
		dependent.setEnroleeId(dependentRequest.getEnroleeId());
		dependent.setName(dependentRequest.getDependentName());
		dependent.setDob(dependentRequest.getDependentDob());
		dependent.setCreatedTimestamp(new Date());
		dependent.setUpdatedTimestamp(new Date());
		enrolee.getDependents().add(dependent);
		return enroleeRepository.save(enrolee);
	}

	public Enrolee editDependent(@Valid DependentRequest dependentRequest) {
		Enrolee enrolee = enroleeRepository.findById(dependentRequest.getEnroleeId()).get();
		Dependent dependent = enrolee.getDependents().stream()
				.filter(dep -> dep.getDependentId().equals(dependentRequest.getDependentId())).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Dependent", "id", dependentRequest.getDependentId()));
		dependent.setEnroleeId(dependentRequest.getEnroleeId());
		dependent.setName(dependentRequest.getDependentName());
		dependent.setDob(dependentRequest.getDependentDob());
		dependent.setUpdatedTimestamp(new Date());
		enrolee.getDependents().add(dependent);
		return enroleeRepository.save(enrolee);
	}

	public void deleteDependent(@NotNull final Long enroleeId, @NotNull final Long dependentId) {
		Enrolee enrolee = enroleeRepository.findById(enroleeId)
				.orElseThrow(() -> new ResourceNotFoundException("Enrolee", "id", enroleeId));
		enrolee.getDependents().stream().filter(dep -> dep.getDependentId().equals(dependentId)).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Dependent", "id", dependentId));
		enrolee.getDependents().removeIf(dependent -> dependent.getDependentId().equals(dependentId));
		enroleeRepository.save(enrolee);
	}

}
