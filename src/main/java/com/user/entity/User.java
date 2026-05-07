package com.user.entity;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Size(min = 6)
	private String name;
	
	@Email
	private String email;
	
	@Size(min = 6)
	private String password;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Address> address;
	
	private String phone;

}
