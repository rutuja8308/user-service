package com.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.entity.User;
import com.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User searchUser(Integer id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public String deleteUser(Integer id) {
		// TODO Auto-generated method stub
		 userRepository.deleteById(id);
		 return "Deleted Successfully";
	}

	@Override
	public List<User> findAllUser() 
	{
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
		return users;
	}

}
