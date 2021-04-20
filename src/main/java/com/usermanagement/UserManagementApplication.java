package com.usermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.usermanagement.service.ProfileService;

@SpringBootApplication
public class UserManagementApplication implements CommandLineRunner{

	@Autowired
	private ProfileService userService;
	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Profile yogesh = Profile.of(UserRequest.of("Yogesh", "1234", "9e170a81-048d-4847-aaeb-6c2ae56da88c.jpg"));
//		Profile satinder = Profile.of(UserRequest.of("Satinder", "12347"));
//		Profile shivam = Profile.of(UserRequest.of("Shivam", "1234"));
//		Profile payal = Profile.of(UserRequest.of("Payal", "1234"));
//		Profile jatin = Profile.of(UserRequest.of("Jatin", "1234"));
//	    List<Profile> users  = Arrays.asList(yogesh, satinder,shivam,payal, jatin);
//	    userService.createInBatch(users);
		
	}
}
