package com.user.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.user.dto.UserDTO;
import com.user.entity.User;
import com.user.exception.ResourceNotFoundException;
import com.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService 
{
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User saveUser(UserDTO user) 
	{
		log.info("User details saved successfully {}", user);
		
	    User savedUser = user.toUser();

		savedUser.setIsDeleted(Boolean.FALSE);
	    
		return userRepository.save(user.toUser());
	}

	@Override
	public User searchUser(String id) 
	{
		log.info("User found in UserServiceImpl {}", id);
			return userRepository.findByIsDeletedFalse();
	}

	@Override
	public String deleteUser(String id) 
	{
		 User user = userRepository.findById(id)
		            .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
		 
		 log.info("id is checking : {}", user);
		 
		 user.setIsDeleted(Boolean.TRUE);
		 
		 userRepository.save(user);
		 
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
	public User updateUser(UserDTO userDto, String id) {
		User existingUser = userRepository.findById(id).orElse(null);
		
		if (existingUser == null) {
	        log.warn("User not found with id: {}", id);
	        return null;
	    }
		
	    User updatedData = userDto.toUser();

		existingUser.setName(userDto.getName());
		existingUser.setEmail(userDto.getEmailId());
		existingUser.setPassword(userDto.getPassword());
		
		existingUser.setAddress(updatedData.getAddress());
		
	    existingUser.getAddress().forEach(a -> a.setUser(existingUser));
		
	    User updatedUser = userRepository.save(existingUser);
		log.info("User updated successfully UserServiceImpl {}", updatedUser);
		return updatedUser;
	}
}
