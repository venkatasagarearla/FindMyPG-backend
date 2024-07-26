package com.example.bookMyPg.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.bookMyPg.exception.ResourceNotFoundException;
import com.example.bookMyPg.model.Bed;
import com.example.bookMyPg.model.Pg;
import com.example.bookMyPg.model.Room;
import com.example.bookMyPg.repository.BedRepository;
import com.example.bookMyPg.repository.PgRepository;
import com.example.bookMyPg.repository.RoomRepository;
import com.example.bookMyPg.response.BedResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceImplementation implements RoomService {
 private final RoomRepository roomRepository;
 private final PgRepository pgRepository;
 private final BedRepository bedRepository;
	
	@Override
	public Room addBedsToRoom(Long pgId, Long roomId, List<Bed> beds) {
	Pg pg=pgRepository.	findById(pgId).orElseThrow(()->new ResourceNotFoundException("Pg not found with given Id"+pgId));
	Room room = pg.getRooms().stream()
			.filter(r -> r.getRoomId().equals(roomId))
			.findFirst()
			.orElseThrow
			(() -> new ResourceNotFoundException("Room not found for the given PG"));
//	if(beds.size()!=room.getNumberOfBeds()) {
//		 throw new IllegalArgumentException("The number of beds provided does not match the room's bed capacity");
//	}
	
	int currentBedCount=room.getBeds().size();
	 int newbedsCount= beds.size(); 
	 if(currentBedCount+newbedsCount>room.getNumberOfBeds()) {
		 throw new IllegalArgumentException("Adding these beds would exceed the room's capacity.");
	 }
	for (Bed bed : beds) {
		int nextBedNumber=bedRepository.getNextBedNumber(roomId);
	    bed.setRoom(room);
	    bed.setBedNumber(nextBedNumber);
	    bedRepository.save(bed); // Save each bed individually
	    room.getBeds().add(bed);
	}

	; // Update the room's bed list
	roomRepository.save(room);

		return room;
	}

	@Override
	public List<BedResponse> getBedResponse(Room addBed) {
		
		return addBed.getBeds().stream().map(bed->new BedResponse(
				bed.getBedId(),
				bed.getBedNumber(),
				bed.getRoomRent(),
				bed.getAdvance(),
				bed.isBooked(),
				addBed.getRoomNumber(),
				addBed.getPg().getPgId())).collect(Collectors.toList());
	}
	}


