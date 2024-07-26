package com.example.bookMyPg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bookMyPg.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	 @Query("SELECT COALESCE(MAX(r.roomNumber), 0) + 1 FROM Room r WHERE r.pg.pgId = :pgId")
	    int getNextRoomNumber(@Param("pgId") Long pgId);
}
