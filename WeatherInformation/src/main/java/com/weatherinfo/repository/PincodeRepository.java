package com.weatherinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weatherinfo.model.Pincode;

@Repository
public interface PincodeRepository extends JpaRepository<Pincode, Integer> {

}