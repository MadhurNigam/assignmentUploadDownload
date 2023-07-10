package com.nv.ImageUploadService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
@Service
public class UploadImageImplementation implements UploadInterface {
	
	
	String uniqueFilename = "";
	String originalFileName="";
	
	
	private static final String UPLOAD_DIR = "C:/Users/Novelvox/Desktop/fileAssignment";

	/**
	 * 
	 */
	public ResponseEntity<String> uploadImage(MultipartFile file) {
	    if (file.isEmpty()) {
	        return ResponseEntity.badRequest().body("Please select a file to upload.");
	    }

	    try {
	        // Generate a unique filename
	        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
	        String uniqueFilename = generateUniqueFilename(originalFilename);
	        this.originalFileName = uniqueFilename;
	        System.out.println(this.originalFileName);

	        // Create a new file in the specified directory
	        File targetFile = new File(UPLOAD_DIR, uniqueFilename);

	        // Create an output stream to write the file
	        FileOutputStream outputStream = new FileOutputStream(targetFile);

	        // Read bytes from the input stream and write to the output stream
	        byte[] buffer = new byte[1024];
	        int bytesRead;
	        InputStream inputStream = file.getInputStream();
	        
	        while ((bytesRead = inputStream.read(buffer)) != -1) {
	            outputStream.write(buffer, 0, bytesRead);
	        }

	        // Close the streams
	        inputStream.close();
	        outputStream.close();

	        return ResponseEntity.status(HttpStatus.OK).body("File Uploaded Successfully");
	    } catch (IOException e) {

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL_SERVER_ERROR");
	    }
	}

	@Override
	public String getDownloadLink() {
        String filename = this.originalFileName;
        String filePath = UPLOAD_DIR + "/" + filename;
        return filePath;
}
	@Override
    public String getFileName() {
    	System.out.print(this.originalFileName);
    	return this.originalFileName;
    }
	@Override
	public ResponseEntity<byte[]> downloadFile() {
	    try {
	        String filename = this.originalFileName;
	        String filePath = UPLOAD_DIR + "/" + filename;

	        // Create a file resource from the specified file path
	        File file = new File(filePath);

	        // Check if the file exists
	        if (!file.exists()) {
	            return ResponseEntity.notFound().build();
	        }

	        // Read the file content into a byte array
	        byte[] fileContent = Files.readAllBytes(file.toPath());

	        // Set the appropriate headers for the response
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        headers.setContentDispositionFormData("attachment", filename);

	        // Return the file content as a ResponseEntity with the appropriate headers
	        return ResponseEntity.ok()
	                .headers(headers)
	                .body(fileContent);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(null);
	    }
	}




	@Override
	public String generateUniqueFilename(String originalFilename) {
		String timestamp = String.valueOf(System.currentTimeMillis());
        String extension = StringUtils.getFilenameExtension(originalFilename);
        return "file_" + timestamp + "." + extension;
	}
}
