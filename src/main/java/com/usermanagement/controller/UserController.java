package com.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.dto.UserRequest;
import com.usermanagement.dto.UserResponse;
import com.usermanagement.model.User;
import com.usermanagement.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1.0/users")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<UserResponse>> getAll() {
		log.info("request for all users");
		return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> get(@PathVariable("id") final Long id) {
		log.info("request for getting user by id : {}", id);
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
	}

	@PostMapping
	public ResponseEntity<UserResponse> create(@RequestBody final UserRequest request) {
		log.info("request for creating user : {}", request);
		User user = User.of(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> update(@PathVariable("id") final Long id, @RequestBody final UserRequest request) {
		log.info("request for updating user by id : {}", id);
		User user = User.of(request);
		return ResponseEntity.status(HttpStatus.OK).body(userService.update(id, user));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") final Long id) {
		log.info("request for deleting user by id : {}", id);
		userService.deleteUserById(id);
	}

}
