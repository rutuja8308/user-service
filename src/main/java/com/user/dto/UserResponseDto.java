package com.user.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto implements Serializable 
{
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String username;
    private String name;
    private String email;
    private String phone;
}