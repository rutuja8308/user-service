package com.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User 
{
	@Id
	private int id;
	
	@Size(min = 6)
	private String name;
	
	@Email
	private String email;
	
	@Size(min = 6)
	private String password;
	
	private String city;

}
