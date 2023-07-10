package com.nv.ImageUploadService;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface UploadInterface {
	public ResponseEntity<String> uploadImage(MultipartFile file);
	public String getDownloadLink();
	public ResponseEntity<byte[]> downloadFile();
	public String generateUniqueFilename(String originalFilename);
    public String getFileName();
}
