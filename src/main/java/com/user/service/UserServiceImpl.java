package com.user.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.user.dto.UserDTO;
import com.user.dto.UserResponseDto;
import com.user.entity.Address;
import com.user.entity.Role;
import com.user.entity.User;
import com.user.exception.ResourceNotFoundException;
import com.user.repository.RoleRepository;
import com.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService 
{
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserRepository userRepository;
	
	private RoleRepository roleRepository;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public User saveUser(UserDTO userDto)
	{
	    // 1. Create user
	    User user = User.builder()
	            .name(userDto.getName())
	            .username(userDto.getUsername())
	            .email(userDto.getEmailId())
	            .password(userDto.getPassword())
	            .phone(userDto.getPhone())
	            .isDeleted(false)
	            .build();

	    // 2. ROLES FIRST (VERY IMPORTANT)
	    Set<Role> roleEntities = userDto.getRoles()
	            .stream()
	            .map(roleName -> roleRepository.findByRoleName(roleName)
	                    .orElseThrow(() ->
	                            new RuntimeException("Role not found: " + roleName)))
	            .collect(Collectors.toSet());

	    user.setRoles(roleEntities);   // ✅ BEFORE SAVE

	    // 3. SAVE USER ONCE
	    User savedUser = userRepository.save(user);

	    // 4. ADDRESS
	    if (userDto.getAddress() != null) {

	        List<Address> addressList = userDto.getAddress()
	                .stream()
	                .map(a -> Address.builder()
	                        .blockNo(a.getBlockNo())
	                        .building(a.getBuilding())
	                        .landmark(a.getLandmark())
	                        .city(a.getCity())
	                        .pin(a.getPin())
	                        .user(savedUser)
	                        .build())
	                .collect(Collectors.toList());

	        savedUser.setAddress(addressList);
	    }

	    return userRepository.save(savedUser);
	}

	@Override
	public User searchUser(String id) 
	{
		log.info("User found in UserServiceImpl {}", id);
			return userRepository.findByIdAndIsDeletedFalse(id);
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
		
	  //  User updatedData = userDto.toUser();

		existingUser.setName(userDto.getName());
		existingUser.setEmail(userDto.getEmailId());
		existingUser.setPassword(userDto.getPassword());
		
		//existingUser.setAddress(updatedData.getAddress());
		
	    existingUser.getAddress().forEach(a -> a.setUser(existingUser));
		
	    User updatedUser = userRepository.save(existingUser);
		log.info("User updated successfully UserServiceImpl {}", updatedUser);
		return updatedUser;
	}

	@Override
	public UserResponseDto searchUserId(String id)
	{
	    System.out.println("******** DB HIT ********");
		
	    User user = userRepository.findByIdAndIsDeletedFalse(id);

	    return UserResponseDto.builder()
	            .id(user.getId())
	            .username(user.getUsername())
	            .name(user.getName())
	            .email(user.getEmail())
	            .phone(user.getPhone())
	            .build();
	}
}
