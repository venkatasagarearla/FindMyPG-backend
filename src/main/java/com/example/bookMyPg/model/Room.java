package com.example.bookMyPg.model;

import java.util.ArrayList;
import java.util.List;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor

public class Room {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long roomId;
	    private int roomNumber;
	    private String roomType;
	    private int numberOfBeds;
	   

	    @ManyToOne
	    @JoinColumn(name = "pg_id")
	    private Pg pg;

	    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Bed> beds = new ArrayList<>();
	    @OneToMany
		 private List<BookedBed> bookings;
	    public Room() {
			 this.beds=new ArrayList<>();
			 this.bookings=new ArrayList<>();
		 }
	    public boolean isFullyOccupied() {
	    	for(Bed bed:beds) {
	    		if(!bed.isOccupied()) {
	    			return false;
	    			
	    		}
	    	}
	    	return true;
	    }
		
}
