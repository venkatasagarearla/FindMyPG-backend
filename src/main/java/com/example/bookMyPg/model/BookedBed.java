package com.example.bookMyPg.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookedBed {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long bookingId;
	
	private String fullName;
	
	
	private String lastName;
	  private String gender;
	
	private Long phoneNumber;
	
	private String emailId;
	
	private LocalDate checkInDate;

	private LocalDate checkOutDate;
 
	private String bookingConformationCode;
	
	 // bed_id is a forgien key.booked bed is associated with one bed.a single bed can have multiple bookings over time but each booking belongs to one specific bed
    @ManyToOne
    @JoinColumn(name = "bed_id")
    private Bed bed;

    // Reference to the room that the bed belongs to.a room can have multiple beds and multiple bookings,but each booking is tied to one specific room
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    // Reference to the pg that the room  belongs to.it connects the booking to the over all property that contains the rooms and beds.a pg can have  multiple rooms and beds hence multiple bookings,but each booking is linked to specific pg
    @ManyToOne
    @JoinColumn(name = "pg_id")
    
    private Pg pg;
}
