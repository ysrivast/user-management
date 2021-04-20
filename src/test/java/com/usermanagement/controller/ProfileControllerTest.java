package com.usermanagement.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.HttpMediaTypeNotSupportedException;

import com.usermanagement.exception.UserException;
import com.usermanagement.service.ProfileService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProfileController.class)
@ActiveProfiles("mysql")
class ProfileControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProfileService profileService;

	@Test
	void testUpdatePhoto_shouldReturnSuccessStatus() throws Exception {

		MockMultipartFile photo = new MockMultipartFile("image", "test.jpg", "image/jpeg",
				"test image content".getBytes());
		doNothing().when(profileService).updatePhoto(1l, photo);
		this.mockMvc.perform(((MockMultipartHttpServletRequestBuilder) multipart("/profiles/1/photo")
				.with(new RequestPostProcessor() {
					@Override
					public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
						request.setMethod("PUT");
						return request;
					}
				})).file(photo).contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void whenUpdatePhotoForUserNotExist_shouldReturnUserException() throws Exception {

		MockMultipartFile photo = new MockMultipartFile("image", "test.jpg", "image/jpeg",
				"test image content".getBytes());
		doThrow(new UserException("User doesn't exist!")).when(profileService).updatePhoto(1l, photo);
		this.mockMvc.perform(((MockMultipartHttpServletRequestBuilder) multipart("/profiles/1/photo")
				.with(new RequestPostProcessor() {
					@Override
					public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
						request.setMethod("PUT");
						return request;
					}
				})).file(photo).contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	void testUpdatePhoto_WhenImageIsNotJpeg_shouldReturnUnsupprtedMediaException() throws Exception {

		MockMultipartFile photo = new MockMultipartFile("image", "test.pdf", "image/jpeg",
				"test image content".getBytes());
		doThrow(new HttpMediaTypeNotSupportedException("unsupported")).when(profileService).updatePhoto(1l, photo);
		this.mockMvc.perform(((MockMultipartHttpServletRequestBuilder) multipart("/profiles/1/photo")
				.with(new RequestPostProcessor() {
					@Override
					public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
						request.setMethod("PUT");
						return request;
					}
				})).file(photo).contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(status().isUnsupportedMediaType());
	}

	@Test
	void testUpdate_WhenPhotoNotSent_shouldReturnBadRequest() throws Exception {

		this.mockMvc.perform(((MockMultipartHttpServletRequestBuilder) multipart("/profiles/1/photo")
				.with(new RequestPostProcessor() {
					@Override
					public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
						request.setMethod("PUT");
						return request;
					}
				})).contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	void testGetPhotoByUserId_shouldReturnByteArray() throws Exception {

		MockMultipartFile photo = new MockMultipartFile("image", "test.jpg", "image/jpeg",
				"test image content".getBytes());
		when(profileService.getImage(1l)).thenReturn(photo.getBytes());
		this.mockMvc.perform(get("/profiles/1/photo")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().bytes(photo.getBytes()));
	}

	@Test
	void testGetPhotoByUserId_shouldReturnBadRequest() throws Exception {

		this.mockMvc.perform(get("/profiles/{userId}/photo","a"))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}

	@Test
	void testGetPhotoByUserId_shouldReturnUserNotFound() throws Exception {

		doThrow(new UserException("User doesn't exist!")).when(profileService).getImage(1l);
		this.mockMvc.perform(get("/profiles/1/photo")).andDo(print()).andExpect(status().isNotFound());
	}

}
