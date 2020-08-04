package com.smart_home.s_home.service.impl;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_home.s_home.data.UserDao;
import com.smart_home.s_home.model.User;
import com.smart_home.s_home.model.UserDto;
import com.smart_home.s_home.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service(value = "userService")
public class UserServiceImpl implements  UserService {
	
	@Autowired
	private UserDao userDao;

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(int id) {
		userDao.deleteById(id);
	}

	@Override
	public User findOne(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public User findById(int id) {
		Optional<User> optionalUser = userDao.findById(id);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}
	
	@Override
	public String  findUsernameById(int id) {
		String username = "";
		User user = findById(id);
		if(user != null) {
			username =  user.getUsername();
		}
		
		return username;
	}

    @Override
    public UserDto update(UserDto userDto) {
        User user = findById(userDto.getId());
        if(user != null) {
            BeanUtils.copyProperties(userDto, user, "password");
            userDao.save(user);
        }
        return userDto;
    }

    @Override
    public User save(UserDto user) {
	    User newUser = new User();
	    newUser.setUsername(user.getUsername());
	    newUser.setEmailAddress(user.getEmailAddress());
	    newUser.setFirstName(user.getFirstName());
	    newUser.setLastName(user.getLastName());
		newUser.setAge(user.getAge());
		newUser.setCreatedBy(user.getUsername());
		newUser.setCreatedDate(user.getCreatedDate());
		newUser.setModifyBy(user.getUsername());
		newUser.setModifyDate(user.getModifyDate());
		newUser.setAddress(user.getAddress());
		newUser.setPhone(user.getPhone());
        return userDao.save(newUser);
    }
}
