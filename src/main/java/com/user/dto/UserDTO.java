package com.user.dto;

import java.util.List;

import com.user.entity.Address;
import com.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private String name;

	private String emailId;

	private String password;

	private List<AddressDTO> address;

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