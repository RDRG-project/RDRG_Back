package com.rdrg.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rdrg.back.dto.request.board.PostBoardRequestDto;
import com.rdrg.back.dto.request.board.PostCommentRequestDto;
import com.rdrg.back.dto.request.board.PutBoardRequestDto;
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

    @PostMapping("/")
    ResponseEntity<ResponseDto> postBoard(
        @RequestBody @Valid PostBoardRequestDto requestBody,
        @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<ResponseDto> response = boardService.postBoard(requestBody, userId);
        return response;
    }

    @PostMapping("/{receptionNumber}/comment")
    public ResponseEntity<ResponseDto> postComment(
        @RequestBody @Valid PostCommentRequestDto requestBody,
        @PathVariable("receptionNumber") int receptionNumber
    ) {
        ResponseEntity<ResponseDto> response = boardService.postComment(requestBody, receptionNumber);
        return response;
    }

    @PutMapping("/{receptionNumber}")
    public ResponseEntity<ResponseDto> putBoard(
        @RequestBody @Valid PutBoardRequestDto requestBody,
        @PathVariable("receptionNumber") int receptionNumber,
        @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<ResponseDto> response = boardService.putBoard(requestBody, receptionNumber, userId);
        return response;
    }

    @DeleteMapping("/{receptionNumber}")
    public ResponseEntity<ResponseDto> deleteBoard(
        @PathVariable("receptionNumber") int receptionNumber,
        @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<ResponseDto> response = boardService.deleteBoard(receptionNumber, userId);
        return response;
    }
}
