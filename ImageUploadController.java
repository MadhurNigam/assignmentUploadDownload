package com.nv.Image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.nv.ImageUploadService.UploadImageImplementation;
@CrossOrigin(origins = "http://localhost:4200/")
@RestController
//@RequestMapping("/api/images")
public class ImageUploadController {
	// Specify the directory where files are uploaded
	@Autowired
	UploadImageImplementation uploadImage;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
		return this.uploadImage.uploadImage(file);
	}

	@GetMapping("/download/link")
	public  String  getDownloadLink() {
		return this.uploadImage.getDownloadLink();
	}

	@GetMapping("/download")
	 public ResponseEntity<byte[]> downloadfileLocally() {
		System.out.print("Calling function");
		return this.uploadImage.downloadFile();
	}
    @GetMapping("/fileName")
    public String originalFileName() {
    	return this.uploadImage.getFileName();
    }
    
    @GetMapping("/getPath")
    public String getLink() {
    	return this.uploadImage.getDownloadLink();
    }
	// Helper method to generate a unique filename
	private String generateUniqueFilename(String originalFilename) {
		return this.generateUniqueFilename(originalFilename);
	}
}
