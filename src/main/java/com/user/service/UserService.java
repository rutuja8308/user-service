package com.user.service;

import java.util.List;

import com.user.entity.User;

public interface UserService 
{
	User saveUser(User user);
	User searchUser(Integer id);
	List<User> findAllUser();
	User updateUser(User user, Integer id);
	String deleteUser(Integer id);
	
}
