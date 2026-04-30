package com.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.user.entity.User;
import com.user.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController 
{   
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserService userService;
	
    @GetMapping
	public String home() 
	{
		return "home controller";	
	}
   
    @PostMapping("/save")
    public User saveUser(@Valid @RequestBody User user)
    {
    	log.info("saveUser method UserController {}", user);
    	return userService.saveUser(user);
    }
    
    @GetMapping("/search/{id}")
    public User searchUser(@PathVariable Integer id)
    {
    	log.info("Search User method UserController {}", id);
		return userService.searchUser(id); 
	}
    
    @GetMapping("/findAll")
    public List<User> findAllUser()
    {
    	log.info("findAllUser method");
    	return userService.findAllUser();
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteUser (@PathVariable Integer id)
    {
    	log.info("User Delete method UserController {}", id);
		return userService.deleteUser(id); 
	}
    
    @PutMapping("/update/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Integer id)
    {
    	log.info("User update method UserController {}", user);
    	return userService.updateUser(user, id);
    }
}