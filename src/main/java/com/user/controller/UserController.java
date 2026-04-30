package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.user.entity.User;
import com.user.repository.UserRepository;
import com.user.service.UserService;

@RestController
public class UserController 
{   
	

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
    @GetMapping
	public String home() 
	{
		return "home controller";	
	}
    
    // save Postmapping - "/save"
    
    @PostMapping("/save")
    public User saveUser(@RequestBody User user)
    {
    	return userService.saveUser(user);
    }
    
    @GetMapping("/search/{id}")
    public User searchUser(@PathVariable Integer id)
    {
		return userService.searchUser(id); 
	//userRepository.findById(id).orElse(null);
    }
    
    @GetMapping("/findAll")
    public List<User> findAllUser()
    {
    	return userService.findAllUser();
    			//userRepository.findAll();
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteUser (@PathVariable Integer id)
    {
		//userRepository.deleteById(id);
		return userService.deleteUser(id); 
				//"Deleted Successfully";
    }
    
    @PutMapping("/update/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Integer id)
    {
    	
    //	User users = userRepository.findById(id).orElse(null);
    	
//    	users.setName(user.getName());
//    	users.setEmail(user.getEmail());
//    	users.setPassword(user.getPassword());
//    	users.setCity(user.getCity());
//    	
//    	userRepository.save(users);
    	
    	return userService.updateUser(user, id);
    }
}