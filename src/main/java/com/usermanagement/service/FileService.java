package com.usermanagement.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {


	public String storeFile(MultipartFile file) throws IOException {
		byte[] bytes = file.getBytes();
		String imageId = UUID.randomUUID().toString();
		String filename = imageId+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
		System.out.println("filename :: "+ filename);
		Path path = Paths.get(filename);
		Files.write(path, bytes);
		return filename;
	}
	
	public ByteArrayOutputStream getFile(String fileName) throws IOException {
		File orginalFile = new File(fileName); 
		return createThumbnail(orginalFile, 100);
		
	}
	
	private ByteArrayOutputStream createThumbnail(File orginalFile, Integer width) throws IOException{  
	    ByteArrayOutputStream thumbOutput = new ByteArrayOutputStream();  
	    BufferedImage img = ImageIO.read(new FileInputStream(orginalFile));
	    BufferedImage thumbImg = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC,width, width, Scalr.OP_ANTIALIAS);  
	    ImageIO.write(thumbImg, getFileType(orginalFile), thumbOutput);  
	    return thumbOutput;  
	}
	
	public static String getFileType(File file) {
		String fileName = file.getName();
		return fileName.substring(fileName.lastIndexOf('.') + 1);
	}
}
