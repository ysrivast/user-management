package com.usermanagement.service;

import java.util.List;

import com.usermanagement.dto.UserResponse;
import com.usermanagement.model.User;

public interface UserService {

	List<UserResponse> getAllUsers();

	UserResponse create(User user);

	UserResponse getUserById(Long id);

	UserResponse update(Long id, User user);

	void deleteUserById(Long id);

}