package com.example.bookMyPg.response;


import java.util.List;

import com.example.bookMyPg.model.Pg;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PgResponse {
	  private Long pgId; 
	  private	String pgName;
      private String pgType;
	  private String location;
	  private String address;
	  private Long phoneNumber;
	  private Pg pg;
	  private List<String> base64Photos;
		private String state;
		private String mapsLink;
  public  PgResponse(Long pgId,String pgName,String pgType,String location,String address, Long phoneNumber,String state,String mapsLink, List<String> base64Photos){
    	this.pgId=pgId;
    	this.pgName=pgName;
    	this.pgType=pgType;
    	this.location=location;
    	this.address=address;
    	this.phoneNumber=phoneNumber;
    	this.state=state;
    	this.mapsLink=mapsLink;
    	this.base64Photos=base64Photos;
    }
public PgResponse(Pg pg) {
	this.pg=pg;
}
public void setBase64Photos(List<String> base64Photos) {
	this.base64Photos=base64Photos;
	
}
@Override
public String toString() {
    return "PgResponse{" +
            "pgId=" + pgId +
            ", pgName='" + pgName + '\'' +
            ", pgType='" + pgType + '\'' +
            ", location='" + location + '\'' +
            ", address='" + address + '\'' +
            ", phoneNumber=" + phoneNumber +
            ", base64Photos=" + base64Photos +
            '}';
}
}
