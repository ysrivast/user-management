package com.usermanagement.service;

import java.io.IOException;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import com.usermanagement.exception.UserException;
import com.usermanagement.model.Profile;
import com.usermanagement.repository.ProfileRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileRepository userRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private FileService fileServie;

	@Override
	public void createInBatch(List<Profile> users) {
		userRepository.saveAll(users);
	}

	@Override
	public void updatePhoto(Long userId, @NotNull MultipartFile photo)
			throws IOException, HttpMediaTypeNotSupportedException {
		log.info("Image update request for userid : {} and filename : {}", userId, photo.getOriginalFilename());
		if ("JPEG".equalsIgnoreCase(getFileType(photo.getOriginalFilename()))) {
			throw (new HttpMediaTypeNotSupportedException("unsupported"));
		}
		Profile user = userRepository.findById(userId).orElseThrow(() -> new UserException("User doesn't exist!"));
		String image = fileServie.storeFile(photo);
		user.setImageId(image);
		userRepository.save(user);
	}

	@Override
	public byte[] getImage(Long userId) throws IOException {
		Profile user = userRepository.findById(userId).orElseThrow(() -> new UserException(""));
		return imageService.createThumbnail(user.getImageId(), 100, 100);
	}

	private String getFileType(String filename) {
		return filename.substring(filename.lastIndexOf('.') + 1);
	}
}
