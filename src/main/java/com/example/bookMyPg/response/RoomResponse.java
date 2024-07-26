package com.example.bookMyPg.response;

import java.util.List;

import com.example.bookMyPg.model.Bed;
import com.example.bookMyPg.model.BookedBed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
	  private Long roomId;
	    private int roomNumber;
	    private String roomType;
	    private int numberOfBeds;
	    private List<Bed> beds;
		private List<BookedBed> bookings;
		private Long pgId;

		



public RoomResponse(Long roomId, int roomNumber, String roomType, int numberOfBeds, Long pgId) {
	this.roomId=roomId;
	this.roomNumber=roomNumber;
	this.roomType=roomType;
    this.numberOfBeds=numberOfBeds;
    this.pgId=pgId;
    
}
		
		
	
}
