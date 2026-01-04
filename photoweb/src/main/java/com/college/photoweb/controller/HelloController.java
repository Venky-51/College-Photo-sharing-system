package com.college.photoweb.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Value;
import com.college.photoweb.model.DeleteRequest;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import com.college.photoweb.model.AdminLoginRequest;

@CrossOrigin
@RestController
public class HelloController {

    private List<DeleteRequest> deleteRequests = new ArrayList<>();
    @Value("${upload.dir}")
    private String uploadDir;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello buddy, Backend is Working";
    }

    @PostMapping("/admin/login")
    public Map<String, String> adminLogin(@RequestBody AdminLoginRequest request) {

        Map<String, String> response = new HashMap<>();

        if ("admin".equals(request.getUsername()) &&
                "admin143".equals(request.getPassword())) {

            response.put("message", "Login successful");
        } else {
            response.put("message", "Invalid credentials");
        }

        return response;
    }

    @PostMapping("/admin/upload")
    public Map<String, String> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("event") String event) {

        Map<String, String> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("message", "File is empty");
            return response;
        }

        try {
            // Resolve absolute upload path
            File baseUploadDir = new File(uploadDir).getAbsoluteFile();
            File eventDir = new File(baseUploadDir, event);

            if (!eventDir.exists()) {
                eventDir.mkdirs();
            }

            File destinationFile = new File(eventDir, file.getOriginalFilename());

            // THIS is the critical fix
            file.transferTo(destinationFile);

            response.put("message", "Photo uploaded successfully");

        } catch (Exception e) {
            response.put("message", "Upload failed");
            e.printStackTrace(); // keep this
        }

        return response;
    }

    @PostMapping("/student/request-delete")
    public Map<String, String> requestDelete(@RequestBody DeleteRequest request) {
        deleteRequests.add(request);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Delete request submitted");
        return response;
    }

    @GetMapping("/admin/delete-requests")
    public List<DeleteRequest> getDeleteRequests() {
        return deleteRequests;
    }

    @PostMapping("/admin/approve-delete")
    public Map<String, String> approveDelete(@RequestBody DeleteRequest request) {

        Map<String, String> response = new HashMap<>();

        try {
            File imageFile = new File(
                    uploadDir + File.separator + request.getEvent()
                            + File.separator + request.getImage());

            if (imageFile.exists()) {
                imageFile.delete();
            }

            deleteRequests.removeIf(r -> r.getEvent().equals(request.getEvent()) &&
                    r.getImage().equals(request.getImage()));

            response.put("message", "Image deleted successfully");

        } catch (Exception e) {
            response.put("message", "Delete failed");
            e.printStackTrace();
        }

        return response;
    }

    @GetMapping("/photos/{event}/{filename}")
    public ResponseEntity<Resource> getPhoto(
            @PathVariable String event,
            @PathVariable String filename) {

        try {
            Path filePath = Paths.get(uploadDir, event, filename);

            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + resource.getFilename() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/admin/photos/{event}")
    public List<String> listPhotos(@PathVariable String event) {

        List<String> photoUrls = new ArrayList<>();

        File eventDir = new File(uploadDir + File.separator + event);
        if (!eventDir.exists() || !eventDir.isDirectory()) {
            return photoUrls; // empty list
        }

        File[] files = eventDir.listFiles();

        if (files != null) {
            for (File file : files) {
                String url = "http://localhost:8080/photos/" + event + "/" + file.getName();
                photoUrls.add(url);
            }
        }

        return photoUrls;
    }

    @GetMapping("/events")
    public List<String> getAllEvents() {

        List<String> events = new ArrayList<>();

        File uploadsDir = new File(uploadDir);

        if (!uploadsDir.exists() || !uploadsDir.isDirectory()) {
            return events; // empty list
        }

        File[] folders = uploadsDir.listFiles(File::isDirectory);

        if (folders != null) {
            for (File folder : folders) {
                events.add(folder.getName());
            }
        }

        return events;
    }

}
