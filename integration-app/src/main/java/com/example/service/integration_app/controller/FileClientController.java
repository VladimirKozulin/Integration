package com.example.service.integration_app.controller;

import com.example.service.integration_app.clients.WebClientSender;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/client/file")
@RequiredArgsConstructor
public class FileClientController {

    private final WebClientSender client;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestPart MultipartFile file) {
        client.uploadFile(file);

        return ResponseEntity.ok("File uploaded");
    }

    @GetMapping("/download/filename")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = client.downloadFile(fileName);

        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + fileName);

        headers.setContentType(MediaType.TEXT_PLAIN);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
