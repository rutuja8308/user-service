package com.user.dto;

import java.util.List;

import com.user.entity.Address;
import com.user.entity.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	@NotBlank(message = "Block number is required")
	private String name;
	
	@Email(message = "Invalid Email")
    @NotBlank(message = "Email is required")
	private String emailId;
	
	@NotBlank(message = "Password is required")
	private String password;
	
	@Valid
	private List<AddressDTO> address;
	
	@NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$",
             message = "Phone number must be 10 digits")
	private String phone;

	public User toUser() {
		User user = User.builder().name(name).email(emailId).password(password).phone(phone).build();

		List<Address> addressList = address.stream().map(a ->

		Address.builder().blockNo(a.getBlockNo()).building(a.getBuilding()).landmark(a.getLandmark()).city(a.getCity())
				.pin(a.getPin()).user(user).build()

		).toList();

		user.setAddress(addressList);

		return user;

	}

}