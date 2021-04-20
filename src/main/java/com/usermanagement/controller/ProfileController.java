package com.usermanagement.controller;

import java.io.IOException;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.usermanagement.service.ProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "profile", description = "Profile API")	
@RestController
@RequestMapping("/profiles")
@Slf4j
@Validated
public class ProfileController {

	@Autowired
	private ProfileService userService;

	@Operation(
			tags = "profile",
			summary = "Update image for a user",
			description = "Image will be updated for given userid"
			)
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description ="Ok"), 
			@ApiResponse(responseCode = "400", description = "Invalid id supplied"),
			@ApiResponse(responseCode = "404", description = "userId not found"),
			@ApiResponse(responseCode = "415", description = "Image format not supported, please upload jpeg image") 
			})

	@PutMapping(path = "/{userId}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> updatePhoto(
			@Parameter(description = "Upload image in jpeg format") 
			@NotNull @RequestParam(required = true, name = "image") final MultipartFile photo,
			@Parameter(description = "UserId of the user") 
			@PathVariable(required = true, name = "userId") final Long userId) throws IOException, HttpMediaTypeNotSupportedException {
		log.info("Image update request for userid : {}",userId);
        userService.updatePhoto(userId, photo);
		return ResponseEntity.ok().build();
	}
	
	@Operation(summary = "Get image for a user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "imageid corresponding to userid"),
			@ApiResponse(responseCode = "400", description = "Invalid userid supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "UserId not found", content = @Content) })
	@GetMapping(path = "/{userId}/photo", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage(
			@Parameter(description = "userId of the user")
			@PathVariable(required = true, name = "userId") @NotNull final Long userId) throws IOException {
		log.info("iage update request for userid : {}",userId);
		return ResponseEntity.ok().body(userService.getImage(userId));
	}
}
