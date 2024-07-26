package com.example.bookMyPg.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import net.coobird.thumbnailator.Thumbnails;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookMyPg.exception.ResourceNotFoundException;
import com.example.bookMyPg.model.Pg;
import com.example.bookMyPg.model.Room;
import com.example.bookMyPg.repository.PgRepository;
import com.example.bookMyPg.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PgServiceImplementation implements PgService {
    private final PgRepository pgRepository;
    private final RoomRepository roomRepository;

//	@Override
//	public Pg addNewPg(String pgName, String pgType,String state,String mapsLink, String location, String address, List<MultipartFile> photos,
//			long phoneNumber) throws SQLException, IOException{
//		
//		Pg pg=new Pg();
//		pg.setPgName(pgName);
//		pg.setPgType(pgType);
//		pg.setLocation(location);
//		pg.setAddress(address);
//		pg.setPhoneNumber(phoneNumber);
//		pg.setMapsLink(mapsLink);
//		pg.setState(state);
//		 List<byte[]> photoList = new ArrayList<>();
//	        for (MultipartFile photo : photos) {
//	            if (!photo.isEmpty()) {
//	                byte[] photoBytes = photo.getBytes();
//	                photoList.add(photoBytes);
//	            }
//	        }
//	        pg.setPhotos(photoList);
//	      return  pgRepository.save(pg);
//		
//	}

	@Override
	public Room addNewRoomToPg(Long pgId, String roomType, int numberOfBeds) {
		Pg pg=pgRepository.findById(pgId).orElseThrow(()->new ResourceNotFoundException("pg not found "+pgId));
	 int nextRoomNumber=roomRepository.getNextRoomNumber(pgId);
	 Room newRoom=new Room();
	 newRoom.setPg(pg);
	newRoom.setRoomType(roomType);
	newRoom.setNumberOfBeds(numberOfBeds);
	newRoom.setRoomNumber(nextRoomNumber);
	return roomRepository.save(newRoom);
	 
	}

	

//	@Override
//	public Page<Pg> getPgs(int page, int size) {
//		// TODO Auto-generated method stub
//		return pgRepository.findAll(PageRequest.of(page, size));
//	}
	 private byte[] compressPhoto(byte[] originalPhotoBytes) throws IOException {
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        Thumbnails.of(new ByteArrayInputStream(originalPhotoBytes))
	                  .size(800, 600)
	                  .outputQuality(0.75)
	                  .toOutputStream(outputStream);
	        return outputStream.toByteArray();
	    }

	@Override
	public Pg addNewPg(String pgName, String pgType, String location, String address, List<MultipartFile> photos,
			long phoneNumber, String state, String mapsLink)throws SQLException, IOException {
		Pg pg=new Pg();
		pg.setPgName(pgName);
		pg.setPgType(pgType);
		pg.setLocation(location);
		pg.setAddress(address);
		pg.setPhoneNumber(phoneNumber);
		pg.setMapsLink(mapsLink);
		pg.setState(state);
		 List<byte[]> photoList = new ArrayList<>();
	        for (MultipartFile photo : photos) {
	            if (!photo.isEmpty()) {
	                byte[] photoBytes =compressPhoto(photo.getBytes());
	                photoList.add(photoBytes);
	            }
	        }
	        pg.setPhotos(photoList);
	      return  pgRepository.save(pg);

	
	}



	@Override
	public Pg updatePg(Long pgId, String pgName, String state, String mapsLink, String pgType, String location,
			String address, List<byte[]> photoBytesList, Long phoneNumber) {
	
		Pg pg=pgRepository.findById(pgId).orElseThrow(()->new ResourceNotFoundException("Pg not found"));
		if(pgName!=null) pg.setPgName(pgName);
		if(state!=null) pg.setState(state);
		if(mapsLink !=null) pg.setMapsLink(mapsLink);
		if(pgType!=null) pg.setPgType(pgType);
		if(location!=null) pg.setLocation(location);
		if(address!=null) pg.setAddress(address);
		if(photoBytesList !=null && !photoBytesList.isEmpty()) {
			pg.setPhotos(photoBytesList);
		}
		if(phoneNumber != null) pg.setPhoneNumber(phoneNumber);
		return pgRepository.save(pg);
	}



	@Override
	public List<byte[]> getPgPhotosByPgId(Long pgId) {
		
		Optional<Pg> thePg=pgRepository.findById(pgId);
		if(thePg.isEmpty()) {
			throw new ResourceNotFoundException("Soory ,PG not found");
		}
		return thePg.get().getPhotos();
	}



	@Override
	public Page<Pg> getAllPgs(PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return pgRepository.findAll(pageRequest);
	}



	@Override
	public void deletePg(Long pgId) {
		Optional<Pg> pg=pgRepository.findById(pgId);
		if(pg.isPresent()) {
			pgRepository.deleteById(pgId);
		}
	}



	@Override
	public List<String> getAllLocationsByCity(String state) {
		
		return pgRepository.findDistinctLocationsByCity(state);
	}



	@Override
	public List<String> getAllCities() {
		
		return pgRepository.findDistinictCities();
	}



	@Override
	public Page<Pg> getPgsByLocation(String location, PageRequest pageRequest) {
		
		return pgRepository.findByLocation(location, pageRequest);
	}




	
	

	
	
  
}
