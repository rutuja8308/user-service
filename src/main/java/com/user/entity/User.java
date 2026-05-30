package com.user.entity;

import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User 
{
	@Id
	@UuidGenerator
	private String id;
	
	private String username;
	
	@Size(min = 6)
	private String name;
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;
	
	@Size(min = 6 ,  message = "Invalid password")
	private String password;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Address> address;
	
	@Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
	private String phone;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Column(name = "is_deleted")
    private Boolean isDeleted = false;

}
