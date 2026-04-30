package com.user.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.entity.User;
import com.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService 
{
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveUser(User user) 
	{
		log.info("User details saved successfully {}", user);
		return userRepository.save(user);
	}

	@Override
	public User searchUser(Integer id) 
	{
		log.info("User found in UserServiceImpl {}", id);
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public String deleteUser(Integer id) {
		 userRepository.deleteById(id);
		 log.info("User deleted successfully UserServiceImpl {}", id);
		 return "Deleted Successfully";
	}

	@Override
	public List<User> findAllUser() 
	{
		log.info("All user UserServiceImpl");
		return userRepository.findAll();
	}

	@Override
	public User updateUser(User user, Integer id) {
		User users = userRepository.findById(id).orElse(null);

		users.setName(user.getName());
		users.setEmail(user.getEmail());
		users.setPassword(user.getPassword());
		users.setCity(user.getCity());
		userRepository.save(user);
		log.info("User updated successfully UserServiceImpl {}", user);
		return users;
	}
}
