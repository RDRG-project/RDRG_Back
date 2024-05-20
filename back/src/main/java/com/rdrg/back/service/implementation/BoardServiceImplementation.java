package com.rdrg.back.service.implementation;

import java.util.List;
import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rdrg.back.dto.request.board.PostBoardRequestDto;
import com.rdrg.back.dto.request.board.PostCommentRequestDto;
import com.rdrg.back.dto.request.board.PutBoardRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.board.GetBoardListResponseDto;
import com.rdrg.back.dto.response.board.GetBoardResponseDto;
import com.rdrg.back.entity.BoardEntity;
import com.rdrg.back.repository.BoardRepository;
import com.rdrg.back.repository.UserRepository;
import com.rdrg.back.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplementation implements BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    
    @Override
    public ResponseEntity<ResponseDto> postBoard(PostBoardRequestDto dto, String userId) {

        try {

            boolean isExistUser = userRepository.existsByUserId(userId);
            if (!isExistUser) return ResponseDto.authenticationFailed();

            BoardEntity boardEntity = new BoardEntity(dto, userId);
            boardRepository.save(boardEntity);
            
        } catch (Exception exception) {

            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }

    @Value("${file.url}")private String fileUrl;
    @Value("${file.path}")private String filePath;

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

            return GetBoardResponseDto.success(boardEntity);
            
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

        } catch (Exception exception) {

            exception.printStackTrace();
            return ResponseDto.databaseError();

        }

        return ResponseDto.success();

    }

    
}
