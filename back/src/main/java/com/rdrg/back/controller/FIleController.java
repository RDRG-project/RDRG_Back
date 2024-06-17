package com.rdrg.back.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rdrg.back.service.FileService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/rdrg/file")
@RequiredArgsConstructor
public class FIleController {

    private final FileService fileService;
    
    @GetMapping(value="/{fileName}", produces={MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public Resource getFile(
        @PathVariable("fileName") String fileName
    ) {
        Resource resource = fileService.GetFile(fileName);
        return resource;
    }

        @PostMapping("/upload")
    public String upload(
        @RequestParam("file")MultipartFile file
    ) {
        String url = fileService.upload(file);
        return url;
    }
}
