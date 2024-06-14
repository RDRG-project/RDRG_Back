package com.rdrg.back.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String  upload(MultipartFile file, Integer linkBoardNumber);
    Resource GetFile(String fileName);
}
