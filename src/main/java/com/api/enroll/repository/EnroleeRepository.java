package com.api.enroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.enroll.model.Enrolee;

public interface EnroleeRepository extends JpaRepository<Enrolee, Long>{

}
