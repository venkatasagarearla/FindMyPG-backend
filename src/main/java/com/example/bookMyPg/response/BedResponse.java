package com.example.bookMyPg.response;

import java.math.BigDecimal;
import java.util.List;

import com.example.bookMyPg.model.BookedBed;
import com.example.bookMyPg.model.Room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BedResponse {

	private Long bedId;
	private int bedNumber;
	private BigDecimal roomRent;
	private BigDecimal advance;
	private boolean isBooked=false;
	private boolean isOccupied=false;
    private Room room;
	private List<BookedBed> bookings;
	private int roomNumber;
	private Long pgId;
	public BedResponse(Long bedId,int bedNumber,BigDecimal roomRent,BigDecimal advance,boolean isBooked,int roomNumber,Long pgId) {
		this.bedId=bedId;
		this.bedNumber=bedNumber;
		this.roomRent=roomRent;
		this.advance=advance;
		this.isBooked=isBooked;
		this.roomNumber=roomNumber;
		this.pgId=pgId;
	}
	

}
