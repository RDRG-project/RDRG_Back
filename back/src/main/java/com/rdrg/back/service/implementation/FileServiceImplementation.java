package com.rdrg.back.service.implementation;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rdrg.back.entity.UploadEntity;
import com.rdrg.back.repository.UploadRepository;
import com.rdrg.back.service.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImplementation implements FileService {
    @Value("${file.url}")private String fileUrl;
    @Value("${file.path}")private String filePath;

    private final UploadRepository uploadRepository;

    @Override
    public String upload(MultipartFile file) {
        // 빈파일인지 검증
        if(file.isEmpty()) return null;
        // 원본 파일명  가져오기
        String originalFileName = file.getOriginalFilename();
        // 원본 파일명 확장자를 구함
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        // UUID 혁식의 임의의 문자열 생성
        String uuid = UUID.randomUUID().toString();
        // 저장시 사용할 파일명 생셩()
        String saveFileName = uuid + extension;
        // 저장할 경로 생성
        String savePath = filePath + saveFileName;
        try {
            // 파일 저장
            file.transferTo(new File(savePath));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        // 파일을 불러올 수 있는 경로 생성
        String url = fileUrl + saveFileName;

        return url;
    }

    @Override
    public Resource GetFile(String fileName) {
        Resource resource = null;
        
        try {
            resource = new UrlResource("file:" + filePath + fileName);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        return resource;
    }
}
