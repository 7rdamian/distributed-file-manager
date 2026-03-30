package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FileController {

    @Value("${app.storage.path}")
    private String storagePath;

    @GetMapping("/files")
    public ResponseEntity<List<String>> listFiles() {
        try {
            File folder = new File(storagePath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String[] files = folder.list();
            return ResponseEntity.ok(Arrays.asList(files != null ? files : new String[0]));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/files/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(storagePath).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/files/create")
    public ResponseEntity<String> createFile(@RequestBody com.example.demo.model.FileItemRequest request) {
        try {
            Path newPath = Paths.get(storagePath).resolve(request.getName()).normalize();

            if (request.isDirectory()) {
                java.nio.file.Files.createDirectories(newPath);
            } else {
                java.nio.file.Files.createFile(newPath);
            }
            return ResponseEntity.ok("Created successfully");

        } catch (java.nio.file.FileAlreadyExistsException e) {
            return ResponseEntity.status(409).body("File already exists");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/files/{name}")
    public ResponseEntity<String> deleteFile(@PathVariable String name) {
        try {
            Path targetPath = Paths.get(storagePath).resolve(name).normalize();

            boolean deleted = java.nio.file.Files.deleteIfExists(targetPath);
            if (deleted) {
                return ResponseEntity.ok("Deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/files/content/{name}")
    public ResponseEntity<String> getFileContent(@PathVariable String name) {
        try {
            Path targetPath = Paths.get(storagePath).resolve(name).normalize();
            String content = java.nio.file.Files.readString(targetPath);
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: Could not read file");
        }
    }

    @PostMapping("/files/save/{name}")
    public ResponseEntity<String> saveFileContent(@PathVariable String name, @RequestBody String newContent) {
        try {
            Path targetPath = Paths.get(storagePath).resolve(name).normalize();
            java.nio.file.Files.writeString(targetPath, newContent);
            return ResponseEntity.ok("Saved successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: Could not save");
        }
    }
}