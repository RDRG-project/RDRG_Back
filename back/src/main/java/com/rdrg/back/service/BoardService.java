package com.rdrg.back.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.rdrg.back.dto.request.board.PostBoardRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.board.GetBoardListResponseDto;
import com.rdrg.back.dto.response.board.GetBoardResponseDto;

public interface BoardService {
    
    ResponseEntity<ResponseDto> postBoard(PostBoardRequestDto dto, String userId);

    ResponseEntity<? super GetBoardListResponseDto> getBoardList();
    ResponseEntity<? super GetBoardResponseDto> getBoard(int receptionNumber);

    String  upload (MultipartFile file);
}
