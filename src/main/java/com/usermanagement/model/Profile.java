package com.usermanagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.usermanagement.dto.ProfileDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "USERS")
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	private String userName;
	private String name;
	private String phone;
	private String email;
	private String imageId;
	private boolean isBankUser;
	private boolean isBeingUpgraded;
	private boolean isSecured;
	
	public static Profile of(ProfileDTO userRequest) {
		Profile user =  new Profile();
		user.setName(userRequest.getName());
		user.setPhone(userRequest.getPhone());
		return user;
	}
}
