package com.example.bookMyPg.service;

import java.util.List;

import com.example.bookMyPg.model.Bed;
import com.example.bookMyPg.model.Room;
import com.example.bookMyPg.response.BedResponse;

public interface RoomService {

	Room addBedsToRoom(Long pgId, Long roomId, List<Bed> beds);

	List<BedResponse> getBedResponse(Room addBed);

}
