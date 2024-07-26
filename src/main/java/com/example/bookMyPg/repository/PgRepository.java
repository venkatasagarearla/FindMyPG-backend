package com.example.bookMyPg.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bookMyPg.model.Pg;

public interface PgRepository  extends JpaRepository<Pg, Long>{
	  @Query("SELECT DISTINCT p.location FROM Pg p WHERE p.state = :state")
	    List<String> findDistinctLocationsByCity(@Param("state") String state);
	  
	  @Query("SELECT DISTINCT p.state FROM Pg p")
	  List<String> findDistinictCities();
	  
	  Page<Pg> findByLocation(String location, PageRequest pageRequest);
}
