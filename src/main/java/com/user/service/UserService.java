package com.user.service;

import java.util.List;

import com.user.dto.UserDTO;
import com.user.entity.User;

public interface UserService 
{
	User saveUser(UserDTO user);
	User searchUser(String id);
	List<User> findAllUser();
	User updateUser(UserDTO user, String id);
	String deleteUser(String id);
	
}
