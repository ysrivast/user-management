package com.usermanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usermanagement.dto.UserResponse;
import com.usermanagement.exception.UserException;
import com.usermanagement.model.User;
import com.usermanagement.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	@Override
	public List<UserResponse> getAllUsers() {
		return userRepository.findAll().stream().map(UserResponse::of).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public UserResponse create(final User user) {
		User persistUser = userRepository.save(user);
		return UserResponse.of(persistUser.getId(), persistUser.getName(), persistUser.getContact());
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "users", key = "#id")
	@Override
	public UserResponse getUserById(final Long id) {
		Optional<User> optional = userRepository.findById(id);
		if (!optional.isPresent()) {
			log.error(" user not found by id : {}", id);
			throw new UserException(String.format("user  not found by id : %d", id));
		}
		User user = optional.get();
		return UserResponse.of(user.getId(), user.getName(), user.getContact());
	}

	@Transactional
	@CachePut(value = "users", key = "#id")
	@Override
	public UserResponse update(final Long id, final User user) {
		Optional<User> optional = userRepository.findById(id);
		if (!optional.isPresent()) {
			log.error("user not found by id : {}", id);
			throw new UserException(String.format("user not found by id : %d", id));
		}
		User persistUser = optional.get();
		persistUser.setName(user.getName());
		persistUser.setContact(user.getContact());
		persistUser = userRepository.save(persistUser);
		return UserResponse.of(persistUser.getId(), persistUser.getName(), persistUser.getContact());
	}

	@Transactional
	@CacheEvict(value = "movies", key = "#id")
	@Override
	public void deleteUserById(final Long id) {
		Optional<User> optional = userRepository.findById(id);
		if (!optional.isPresent()) {
			log.error("movie not found by id : {}", id);
			throw new UserException(String.format("movie not found by id : %d", id));
		}
		userRepository.deleteById(id);
	}

}
