package com.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usermanagement.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
