package com.rdrg.back.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rdrg.back.dto.request.board.PostBoardRequestDto;
import com.rdrg.back.dto.request.board.PostCommentRequestDto;
import com.rdrg.back.dto.request.board.PutBoardRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.board.GetBoardListResponseDto;
import com.rdrg.back.dto.response.board.GetBoardResponseDto;
import com.rdrg.back.entity.BoardEntity;
import com.rdrg.back.entity.UploadEntity;
import com.rdrg.back.repository.BoardRepository;
import com.rdrg.back.repository.UploadRepository;
import com.rdrg.back.repository.UserRepository;
import com.rdrg.back.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplementation implements BoardService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final UploadRepository uploadRepository;
    
    @Override
    public ResponseEntity<ResponseDto> postBoard(PostBoardRequestDto dto, String userId) {

        try {
            boolean isExistUser = userRepository.existsByUserId(userId);
            if (!isExistUser) return ResponseDto.authenticationFailed();

            BoardEntity boardEntity = new BoardEntity(dto, userId);
            boardRepository.save(boardEntity);

            Integer receptionNumber = boardEntity.getReceptionNumber();
            List<String> urlList = dto.getUrlList();
            List<UploadEntity> uploadEntities = new ArrayList<>();

            for(String url: urlList) {
                UploadEntity uploadEntity = new UploadEntity(receptionNumber, url);
                uploadEntities.add(uploadEntity);
            }
            uploadRepository.saveAll(uploadEntities);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetBoardListResponseDto> getBoardList() {

        try {
            List<BoardEntity> boardEntities = boardRepository.findByOrderByReceptionNumberDesc();
            return GetBoardListResponseDto.success(boardEntities);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(int receptionNumber) {

        try {
            BoardEntity boardEntity = boardRepository.findByReceptionNumber(receptionNumber);
            if (boardEntity == null) return ResponseDto.noExistBoard();

            List<UploadEntity> uploadEntities = uploadRepository.findByLinkBoardNumber(receptionNumber);
            List<String> imageUrls = uploadEntities.stream().map(UploadEntity::getUrl).collect(Collectors.toList());

            return GetBoardResponseDto.success(boardEntity, imageUrls);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<ResponseDto> postComment(PostCommentRequestDto dto, int receptionNumber) {

        try {
            BoardEntity boardEntity = boardRepository.findByReceptionNumber(receptionNumber);
            if (boardEntity == null) return ResponseDto.noExistBoard();

            boolean status = boardEntity.getStatus();
            if (status) return ResponseDto.writtenComment();

            String comment = dto.getComment();
            boardEntity.setStatus(true);
            boardEntity.setComment(comment);
            boardRepository.save(boardEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> deleteBoard(int receptionNumber, String userId) {

        try {
            BoardEntity boardEntity = boardRepository.findByReceptionNumber(receptionNumber);
            if (boardEntity == null) return ResponseDto.noExistBoard();

            String writerId = boardEntity.getWriterId();
            boolean isWriter = userId.equals(writerId);
            if (!isWriter) return ResponseDto.authorizationFailed();

            boardRepository.delete(boardEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> putBoard(PutBoardRequestDto dto, int receptionNumber, String userId) {

        try {
            
            BoardEntity boardEntity = boardRepository.findByReceptionNumber(receptionNumber);
            if (boardEntity == null) return ResponseDto.noExistBoard();

            String writerId = boardEntity.getWriterId();
            boolean isWriter = userId.equals(writerId);
            if (!isWriter) return ResponseDto.authenticationFailed();

            boolean status = boardEntity.getStatus();
            if (status) return ResponseDto.writtenComment();

            boardEntity.update(dto);
            boardRepository.save(boardEntity);

            List<UploadEntity> existingUploads = uploadRepository.findByLinkBoardNumber(receptionNumber);
            for (UploadEntity uploadEntity : existingUploads) {
            uploadRepository.delete(uploadEntity);
            }

            receptionNumber = boardEntity.getReceptionNumber();
            List<String> urlList = dto.getUrlList();
            List<UploadEntity> uploadEntities = new ArrayList<>();

            for(String url: urlList) {
                UploadEntity uploadEntity = new UploadEntity(receptionNumber, url);
                uploadEntities.add(uploadEntity);
            }
            uploadRepository.saveAll(uploadEntities);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }
}
