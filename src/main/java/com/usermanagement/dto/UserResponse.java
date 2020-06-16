package com.usermanagement.dto;

import java.io.Serializable;

import com.usermanagement.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@Setter
@Getter
public class UserResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String contact;

	public static UserResponse of (User user) {
		return of(user.getId(), user.getName(), user.getContact());
	
	}
}
