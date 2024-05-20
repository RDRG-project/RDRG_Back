package com.rdrg.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rdrg.back.dto.request.board.PostBoardRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.board.GetBoardListResponseDto;
import com.rdrg.back.dto.response.board.GetBoardResponseDto;
import com.rdrg.back.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rdrg/board")
@RequiredArgsConstructor
public class BoardController {
    
    private final BoardService boardService;

    @PostMapping("/")
    ResponseEntity<ResponseDto> postBoard(
    @RequestBody @Valid PostBoardRequestDto requestBody,
    @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<ResponseDto> response =boardService.postBoard(requestBody, userId);
        return response;
    }

    @PostMapping("/upload")
    public String upload(
        @RequestParam("file")MultipartFile file
    ) {
        String url = boardService.upload(file);
        return url;
    }

    @GetMapping("/list")
    public ResponseEntity<? super GetBoardListResponseDto> getBoardList() {
        ResponseEntity<? super GetBoardListResponseDto> response = boardService.getBoardList();
        return response;
    }

    @GetMapping("/{receptionNumber}")
    public ResponseEntity<? super GetBoardResponseDto> getBoard(
    @PathVariable("receptionNumber") int receptionNumber
    ) {
        ResponseEntity<? super GetBoardResponseDto> response = boardService.getBoard(receptionNumber);
        return response;
    }
}