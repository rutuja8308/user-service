package com.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address 
{
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
	@JdbcTypeCode(SqlTypes.VARCHAR)
	private String id;
	
	private String blockNo;
	
	private String building;
	
	private String landmark;
	
	private String city;
	
	private int pin;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
}
