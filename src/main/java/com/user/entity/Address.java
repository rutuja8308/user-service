package com.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@UuidGenerator
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
	@ToString.Exclude
	private User user;
}
