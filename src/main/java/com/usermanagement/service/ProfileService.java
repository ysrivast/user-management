package com.usermanagement.service;

import java.io.IOException;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import com.usermanagement.model.Profile;

public interface ProfileService {

	void createInBatch(List<Profile> users);

	void updatePhoto(Long userId, @NotNull MultipartFile photo) throws IOException, HttpMediaTypeNotSupportedException;

	byte[] getImage(Long userId) throws IOException;

}