package com.example.bookMyPg.model;

import java.math.BigDecimal;
import java.util.List;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bed {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long bedId;
	private int bedNumber;
	private BigDecimal roomRent;
	private BigDecimal advance;
	private boolean isBooked=false;
	private boolean isOccupied=false;
	// many beds belongs to specific room
	 @ManyToOne
	    @JoinColumn(name = "room_id")
	    private Room room;
	  
	 // one bed can book many times
	   @OneToMany(mappedBy = "bed", fetch = FetchType.LAZY, cascade = CascadeType.ALL)

	  private List<BookedBed> bookings;
}
