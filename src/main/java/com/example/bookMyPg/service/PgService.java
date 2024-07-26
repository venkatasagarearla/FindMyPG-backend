package com.example.bookMyPg.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookMyPg.model.Pg;
import com.example.bookMyPg.model.Room;

public interface PgService {

//	Pg addNewPg(String pgName, String pgType,  String state,String mapsLink,String location, String address, List<MultipartFile> photos,
//			long phoneNumber) throws SQLException,IOException;

	

	Room addNewRoomToPg(Long pgId, String roomType, int numberOfBeds);



	



//	Page<Pg> getPgs(int page, int size);



	Pg addNewPg(String pgName, String pgType, String location, String address, List<MultipartFile> photos,
			long phoneNumber, String state, String mapsLink)throws SQLException, IOException;







	Pg updatePg(Long pgId, String pgName, String state, String mapsLink, String pgType, String location, String address,
			List<byte[]> photoBytesList, Long phoneNumber);







	List<byte[]> getPgPhotosByPgId(Long pgId);







	Page<Pg> getAllPgs(PageRequest pageRequest);







	void deletePg(Long pgId);







	List<String> getAllLocationsByCity(String state);







	List<String> getAllCities();







	Page<Pg> getPgsByLocation(String location, PageRequest pageRequest);







	







	



	



	



	


}
