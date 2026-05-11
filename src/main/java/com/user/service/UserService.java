package com.user.service;

import java.util.List;
import java.util.UUID;

import com.user.dto.UserDTO;
import com.user.entity.User;

public interface UserService 
{
	User saveUser(UserDTO user);
	User searchUser(String id);
	List<User> findAllUser();
	User updateUser(User user, String id);
	String deleteUser(String id);
	
}
