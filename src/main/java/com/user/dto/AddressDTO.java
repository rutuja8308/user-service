package com.user.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO 
{
	private String id;

	@NotBlank(message = "Block number is required")
	private String blockNo;

	@NotBlank(message = "Building is required")
	private String building;

	private String landmark;

	@NotBlank(message = "City is required")
	private String city;

	@NotNull(message = "Pin is required")
	@Min(value = 100000, message = "Pin must be 6 digits")
	@Max(value = 999999, message = "Pin must be 6 digits")
	private Integer pin;
}