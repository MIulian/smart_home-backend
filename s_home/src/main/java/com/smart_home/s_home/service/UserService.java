package com.smart_home.s_home.service;

import com.smart_home.s_home.model.User;
import com.smart_home.s_home.model.UserDto;

import java.util.List;

public interface UserService {

	boolean findByUsername(String username);
	
    User save(UserDto user);
    
    List<User> findAll();
    
    void delete(int id);

    User findOne(String username);

    User findById(int id);
    
    String findUsernameById(int id);

    UserDto update(UserDto userDto);
}
