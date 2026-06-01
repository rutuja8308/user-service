package com.user.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.UserDTO;
import com.user.dto.UserResponseDto;
import com.user.entity.User;
import com.user.service.UserService;
import com.user.validationmessages.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController 
{   
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	private UserService userService;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@Value("${my.name}")
	private String name;
	
    @GetMapping("/getFromProp")
	public String home() 
	{	
    	log.info(ValidationMessages.READ_VALUE_FROM_FILE);	
		return "home controller" + name;	
	}
    
    @PostMapping("/save")
    public User saveUser(@Valid @RequestBody UserDTO user)
    {
    	log.info("saveUser method UserController {}", user);
    	return userService.saveUser(user);
    }
    
    @GetMapping("/search/{id}")
    public User searchUser(@PathVariable String id)
    {
    	log.info("Search User method UserController {}", id);
		return userService.searchUser(id); 
	}
    
    @GetMapping("/searchcache/{id}")
    public UserResponseDto searchUserCache(@PathVariable String id)
    {
        return userService.searchUserId(id);
    }
    
    // create product // search product // update
    
    @GetMapping("/findAll")
    public List<User> findAllUser()
    {
    	log.info("findAllUser method");
    	return userService.findAllUser();
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteUser (@PathVariable String id)
    {
    	log.info("User Delete method UserController {}", id);
		return userService.deleteUser(id); 
	}
    
    @PutMapping("/update/{id}")
    public User updateUser(@RequestBody UserDTO user, @PathVariable String id)
    {
    	log.info("User update method UserController {}", user);
    	return userService.updateUser(user, id);
    }
}