package com.company.cla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.company.cla.entity.Owner;



public interface OwnerRepository extends JpaRepository<Owner, Long>{
	@Query(value="SELECT owner_Name FROM owners",nativeQuery=true)
	public List<String> findAllOwners();
}
