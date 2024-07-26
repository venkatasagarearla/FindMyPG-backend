package com.example.bookMyPg.controller;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;

//import org.apache.commons.codec.binary.Base64;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.example.bookMyPg.model.Pg;
import com.example.bookMyPg.model.Room;
import com.example.bookMyPg.response.PgResponse;
import com.example.bookMyPg.response.RoomResponse;
import com.example.bookMyPg.service.PgService;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@CrossOrigin
@RestController
@RequestMapping("/pg")
@RequiredArgsConstructor
public class PgController {

	private final PgService pgService;
	@PostMapping("/add-pg")
	public ResponseEntity<Pg> addnewPg(@RequestParam("pgName") String pgName,@RequestParam("state") String state,@RequestParam("mapsLink") String mapsLink,@RequestParam("pgType") String pgType,@RequestParam("location") String location,@RequestParam("address") String address,@RequestParam("photos") List<MultipartFile> photos, @RequestParam("phoneNumber")long phoneNumber) throws SQLException, IOException{
		Pg response=pgService.addNewPg(pgName,pgType,location,address,photos, phoneNumber,state,mapsLink);
		return ResponseEntity.ok(response);
	}
	@PostMapping("/add-rooms/rooms/{pgId}")
	public ResponseEntity<RoomResponse> addRoomToPg( @PathVariable Long pgId ,@RequestParam("roomType") String roomType,@RequestParam("numberOfBeds") int numberOfBeds){
		Room addRoom=pgService.addNewRoomToPg(pgId,roomType,numberOfBeds);
		RoomResponse response=new RoomResponse(
				addRoom.getRoomId(),addRoom.getRoomNumber(),addRoom.getRoomType(),addRoom.getNumberOfBeds(),addRoom.getPg().getPgId());
		return ResponseEntity.ok(response);
				
	}
//	public ResponseEntity<PgResponse> getPgById(@PathVariable Long pgId){
//		Pg pg=pgService.getPgById(pgId);
//		PgResponse response=new PgResponse(pg.getPgId(),pg.getPgName(),pg.getPgType(),pg.getLocation(),pg.getAddress(),pg.getPhoneNumber());
//		return ResponseEntity.ok(response);
//	}
	
//	@GetMapping("/all-pgs")
//	public ResponseEntity<StreamingResponseBody> getAllPgs(@RequestParam (defaultValue = "0") int page,@RequestParam(defaultValue ="100")int size){
//		System.out.println("hello");
////		List<Pg> pgs=pgService.getAllPgs(page,size);
////		List<PgResponse> response=new ArrayList<>();
////		for(Pg pg:pgs) {
////			//convert photos to base64 strings
////			List<String> base64Photos=new ArrayList<>();
////			for(byte[] photoBytes :pg.getPhotos()) {
////				if(photoBytes!=null && photoBytes.length>0) {
////					String base64Photo=Base64.getEncoder().encodeToString(photoBytes);
////					base64Photos.add(base64Photo);
////				}
////			}
////			PgResponse pgResponse=new PgResponse(pg);
////			pgResponse.setBase64Photos(base64Photos);
////			response.add(pgResponse);
////		}
////		return ResponseEntity.ok(response);
////		
////	}
////	
////	Page<Pg> pgs=pgService.getPgs(page,size);
////	Page<PgResponse> pgResponses=pgs.map(pg->{
////		List<String> base64Photos = new ArrayList<>();
////        for (byte[] photoBytes : pg.getPhotos()) {
////            if (photoBytes != null && photoBytes.length > 0) {
////                String base64Photo = Base64.getEncoder().encodeToString(photoBytes);
////                base64Photos.add(base64Photo);
////            }
////        }
////        PgResponse pgResponse = new PgResponse(pg);
////        pgResponse.setBase64Photos(base64Photos);
////        return pgResponse;
////	});
////	 return ResponseEntity.ok(pgResponses);	
//		StreamingResponseBody stream = new StreamingResponseBody() {
//            @Override
//            public void writeTo(OutputStream outputStream) throws IOException {
//                List<Pg> pgs = pgService.getPgs(page, size).getContent();
//                for (Pg pg : pgs) {
//                    List<String> base64Photos = new ArrayList<>();
//                    for (byte[] photoBytes : pg.getPhotos()) {
//                        if (photoBytes != null && photoBytes.length > 0) {
//                            String base64Photo = Base64.getEncoder().encodeToString(photoBytes);
//                            base64Photos.add(base64Photo);
//                        }
//                    }
//                    PgResponse pgResponse = new PgResponse(pg);
//                    pgResponse.setBase64Photos(base64Photos);
//                    outputStream.write(pgResponse.toString().getBytes());
//                    outputStream.write("\n".getBytes());
//                }
//                outputStream.flush();
//            }
//        };
//
//        return new ResponseEntity<>(stream, HttpStatus.OK);
//	}
	@PutMapping("/updatePg/{pgId}")
	public ResponseEntity<PgResponse> updatePg(@PathVariable  Long pgId,
			@RequestParam(required=false) String pgName,
			@RequestParam(required=false) String state,
			@RequestParam(required=false) String mapsLink,
			@RequestParam(required=false) String pgType,
			@RequestParam(required=false) String location,
			@RequestParam(required=false) String address,
			@RequestParam(required=false) List<MultipartFile> photos, 
			@RequestParam(required=false)Long phoneNumber) throws IOException,SQLException{
		//List<byte[]> photoBytesList used to extract the raw data from the database
		List<byte[]> photoBytesList=new ArrayList<>();
		if(photos !=null && !photos.isEmpty()) {
			for(MultipartFile photo :photos) {
				if(photo !=null && !photo.isEmpty()) {
					photoBytesList.add(photo.getBytes());
				}
			}
		} else {
			photoBytesList=pgService.getPgPhotosByPgId(pgId);
		}
		Pg updatedpg=pgService.updatePg(pgId,pgName,state,mapsLink,pgType,location,address,photoBytesList,phoneNumber);
		List<String> base64Photos = new ArrayList<>();
	    for (byte[] photoBytes : updatedpg.getPhotos()) {
	        base64Photos.add(Base64.getEncoder().encodeToString(photoBytes));
	    }
		PgResponse pgResponse=new PgResponse(updatedpg.getPgId(),updatedpg.getPgName(),updatedpg.getPgType(),updatedpg.getLocation(),updatedpg.getAddress(),updatedpg.getPhoneNumber(),updatedpg.getState(),updatedpg.getMapsLink(),base64Photos);
		  return ResponseEntity.ok(pgResponse);
	}
	private PgResponse getPgResponse(Pg pg) {
     PgResponse pgResponse=new PgResponse(pg);
     List<String> base64Photos=new ArrayList<>();
     for(byte[] photoBytes :pg.getPhotos()) {
    	 if(photoBytes !=null && photoBytes.length>0) {
    		 String base64Photo=Base64.getEncoder().encodeToString(photoBytes);
    		 base64Photos.add(base64Photo);
    	 }
     }
     pgResponse.setBase64Photos(base64Photos);
     return pgResponse;
	}
	@GetMapping("/pgs")
	public ResponseEntity<Page<PgResponse>> getAllPgs(
	        @RequestParam int page, 
	        @RequestParam int size) {
	    Page<Pg> pgPage = pgService.getAllPgs(PageRequest.of(page, size));
	    Page<PgResponse> pgResponsePage = pgPage.map(pg -> {
	    	List<String> photoUrls = pg.getPhotos().stream()
	    		    .map((byte[] photo) -> "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(photo))
	    		    .collect(Collectors.toList());
	        return new PgResponse(pg.getPgId(), pg.getPgName(), pg.getPgType(), pg.getLocation(), 
	                              pg.getAddress(), pg.getPhoneNumber(), pg.getState(), 
	                              pg.getMapsLink(), photoUrls);
	    });
	    return ResponseEntity.ok(pgResponsePage);
	}
    
	@DeleteMapping("/deletepg/{pgId}")
     public ResponseEntity<Void> deletePg(@PathVariable Long pgId){
    	 pgService.deletePg(pgId);
    	 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }
	
@GetMapping	("/location/{state}")
public ResponseEntity<List<String>> getAllLocationsByCity(@PathVariable String state){
	
	List<String> locations=pgService.getAllLocationsByCity(state);
	return ResponseEntity.ok(locations);
}

@GetMapping("/cities")
public ResponseEntity<List<String>> getAllCities( ){
	System.out.println("hello1");
	List<String> cities=pgService.getAllCities();
	return ResponseEntity.ok(cities);
}

	
//public  ResponseEntity<Page<PgResponse>> getPgsByLocation(@PathVariable String location,@RequestParam int page,@RequestParam int size){
//	Page<Pg> pgPage=pgService.getPgsByLocation(location,PageRequest.of(page, size));
////	Page<PgResponse> pgResponsePage=pgPage.map(pg->{List<String> comPressedPhotoUrls=pg.getPhotos().stream().map(photo->"data:image/jpeg;base64,"+Base64.encodeBase64String(compressPhoto(photo))).collect(Collectors.toList());
////	 return new PgResponse(pg.getPgId(),pg.getPgName(),pg.getPgType(),pg.getLocation(),pg.getAddress(),pg.getPhoneNumber(),pg.getState(),pg.getMapsLink(),comPressedPhotoUrls);
////	});
//	  Page<PgResponse> pgResponsePage = pgPage.map(pg -> {
//	        List<String> compressedPhotoUrls = pg.getPhotos().stream()
//	            .map(photo -> {
//	                byte[] compressedPhoto = compressPhoto(photo);  // Compress the photo
//	                return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(compressedPhoto);
//	            })
//	            .collect(Collectors.toList());
//	   return ResponseEntity.ok(pgResponsePage);
//}
//}
@GetMapping("/get-pgbylocation/{location}")
public ResponseEntity<Page<PgResponse>> getPgsByLocation(@PathVariable String location, 
        @RequestParam int page, 
        @RequestParam int size) {
Page<Pg> pgPage = pgService.getPgsByLocation(location, PageRequest.of(page, size));
Page<PgResponse> pgResponsePage = pgPage.map(pg -> {
List<String> compressedPhotoUrls = pg.getPhotos().stream()
.map(photo -> {
byte[] compressedPhoto = compressPhoto(photo);  // Compress the photo
return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(compressedPhoto);
})
.collect(Collectors.toList());
return new PgResponse(pg.getPgId(), pg.getPgName(), pg.getPgType(), pg.getLocation(), 
pg.getAddress(), pg.getPhoneNumber(), pg.getState(), 
pg.getMapsLink(), compressedPhotoUrls);
});
return ResponseEntity.ok(pgResponsePage);
}

private byte[] compressPhoto(byte[] originalPhotoBytes) {
	try {
		
		        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		        Thumbnails.of(new ByteArrayInputStream(originalPhotoBytes))
		                  .size(800, 600)
		                  .outputQuality(0.75)
		                  .toOutputStream(outputStream);
		        return outputStream.toByteArray();
		    
    } catch (IOException e) {
        throw new RuntimeException("Error compressing photo", e);
    }
}
}

	

