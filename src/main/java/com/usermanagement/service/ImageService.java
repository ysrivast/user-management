package com.usermanagement.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import com.usermanagement.exception.GenericException;

@Service
public class ImageService {

	public byte[] createThumbnail(String file, Integer width, Integer height) throws IOException {
		File originalFile = new File(file); 
		if(!originalFile.exists())
			throw new GenericException(String.format("File : %s  doesnot Exist!", file));
		ByteArrayOutputStream thumbOutput = new ByteArrayOutputStream();
		BufferedImage img = ImageIO.read(new FileInputStream(originalFile));
		BufferedImage thumbImg = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, width, height,
				Scalr.OP_ANTIALIAS);
		ImageIO.write(thumbImg, getFileType(originalFile), thumbOutput);
		return thumbOutput.toByteArray();
	}

	private static String getFileType(File file) {
		String fileName = file.getName();
		return fileName.substring(fileName.lastIndexOf('.') + 1);
	}

}
