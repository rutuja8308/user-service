package com.user.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.user.entity.Address;
import com.user.entity.User;
import com.user.exception.ResourceNotFoundException;
import com.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService 
{
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveUser(User user) 
	{
		log.info("User details saved successfully {}", user);
		
		for(Address address : user.getAddress())
		{
			address.setUser(user);
		}
		
		return userRepository.save(user);
	}

	@Override
	public User searchUser(String id) 
	{
		log.info("User found in UserServiceImpl {}", id);
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
	}

	@Override
	public String deleteUser(String id) 
	{
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
	public User updateUser(User user, String id) {
		User existingUser = userRepository.findById(id).orElse(null);
		
		if (existingUser == null) {
	        log.warn("User not found with id: {}", id);
	        return null;
	    }

		existingUser.setName(user.getName());
		existingUser.setEmail(user.getEmail());
		existingUser.setPassword(user.getPassword());
		existingUser.setAddress(user.getAddress());
	    User updatedUser = userRepository.save(existingUser);
		log.info("User updated successfully UserServiceImpl {}", updatedUser);
		return updatedUser;
	}

	
}
