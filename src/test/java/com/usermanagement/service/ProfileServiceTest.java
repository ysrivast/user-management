package com.usermanagement.service;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import com.usermanagement.repository.ProfileRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("h2")
class ProfileServiceTest {

	@MockBean
	private ProfileRepository profileRepository;
	
	@MockBean
	private ImageService imageService; 
	
	@MockBean
	private FileService fileService;
	
	@InjectMocks
	private ProfileServiceImpl profileService;
	
	@Test
	void testUpdateImage() throws IOException, HttpMediaTypeNotSupportedException {
		MultipartFile photo = new MockMultipartFile("image", "test.jpg", "image/jpeg",
				"test image content".getBytes());
//		Profile profile = Profile.of(UserRequest.of("Test", "1234"));
//		when(profileRepository.findById(1l)).thenReturn(Optional.of(profile));
//		profile.setImageId(UUID.randomUUID().toString());
//		when(profileRepository.save(profile)).thenReturn(profile);
		profileService.updatePhoto(1l, photo);
		
	}

}
