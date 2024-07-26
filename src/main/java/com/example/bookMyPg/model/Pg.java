package com.example.bookMyPg.model;

import java.util.ArrayList;
import java.util.List;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pg {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private Long pgId;
    private	String pgName;
    private String pgType;
    private Long phoneNumber;
	private String location;
	private String address;
	private String state;
	private String mapsLink;
	
    @Lob
    @ElementCollection
    @Column(name = "photo", columnDefinition = "LONGBLOB")
    private List<byte[]> photos=new ArrayList<>(); 
 
    // Storing photos as byte arrays
	 @OneToMany
	 ( mappedBy="pg",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	    private List<Room> rooms=new ArrayList<>();

}
