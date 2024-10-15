package com.example.s3_bucket_integration.controller;



import com.example.s3_bucket_integration.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class StorageController {

    @Autowired
    private StorageService service;

    @PostMapping("upload/{folderName}")
    public ResponseEntity<String> uploadFile(
            @PathVariable("folderName") String folderName,
            @RequestParam(value = "file") MultipartFile file) {

        return new ResponseEntity<>(service.uploadFile(folderName, file), HttpStatus.OK);
    }


    @GetMapping("download/{folderName}/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(
            @PathVariable String folderName,
            @PathVariable String fileName) {

        byte[] data = service.downloadFile(folderName, fileName);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }


    @DeleteMapping("/delete/{folderName}/{fileName}")
    public ResponseEntity<String> deleteFile(
            @PathVariable String folderName,
            @PathVariable String fileName) {

        return new ResponseEntity<>(service.deleteFile(folderName, fileName), HttpStatus.OK);
    }

}
