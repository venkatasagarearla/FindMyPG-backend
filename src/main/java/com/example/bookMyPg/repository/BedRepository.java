package com.example.bookMyPg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bookMyPg.model.Bed;

public interface BedRepository  extends JpaRepository<Bed, Long>{
	 @Query("SELECT COALESCE(MAX(b.bedNumber), 0) + 1 FROM Bed b WHERE b.room.roomId = :roomId")
	    int getNextBedNumber(@Param("roomId") Long roomId);
}
