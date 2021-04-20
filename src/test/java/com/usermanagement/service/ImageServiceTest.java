package com.usermanagement.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ImageServiceTest {

	@InjectMocks
	private ImageService imageService; 

	@Test
	void test() throws IOException {
		byte[] by = imageService.createThumbnail("009a03aa-9d33-46bf-af22-107f033fcbd6.jpg", 100, 100);
		assertNotNull(by);
	}

}
