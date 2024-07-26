package com.example.bookMyPg.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.bookMyPg.model.Bed;
import com.example.bookMyPg.model.Room;
import com.example.bookMyPg.response.BedResponse;
import com.example.bookMyPg.service.RoomService;

import lombok.RequiredArgsConstructor;
@CrossOrigin("*")
@RestController
@RequestMapping("/pg/room/addbed")
@RequiredArgsConstructor
public class RoomController {
	private final RoomService roomService;
	@PostMapping("/pg/{pgId}/rooms/{roomId}/beds")
	public ResponseEntity<List<BedResponse>>addBedsToRoom(@PathVariable Long pgId,@PathVariable Long roomId,@RequestBody List<Bed> beds){
		System.out.println("hello");
		Room addBed=roomService.addBedsToRoom(pgId,roomId,beds);
	   List<BedResponse> bedResponses=roomService.getBedResponse(addBed);
		return ResponseEntity.ok(bedResponses);
	}
}
