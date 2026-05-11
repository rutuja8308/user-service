package com.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO 
{
	private String id;
	
	private String blockNo;
	
	private String building;
	
	private String landmark;
	
	private String city;
	
	private Integer pin;
}