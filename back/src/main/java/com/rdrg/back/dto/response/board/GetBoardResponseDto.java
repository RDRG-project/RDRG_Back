package com.rdrg.back.dto.response.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rdrg.back.common.util.ChangeDateFormatUtil;
import com.rdrg.back.dto.response.ResponseCode;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.ResponseMessage;
import com.rdrg.back.entity.BoardEntity;

import lombok.Getter;

@Getter
public class GetBoardResponseDto extends ResponseDto {
    private Integer receptionNumber;
    private boolean status;
    private String title;
    private String writerId;
    private String writeDatetime;
    private String contents;
    private String comment;

    private GetBoardResponseDto(BoardEntity boardEntity) throws Exception {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        String writeDatetime = ChangeDateFormatUtil.changeYYYYMMDD(boardEntity.getWriteDatetime());

        this.receptionNumber = boardEntity.getReceptionNumber();
        this.status = boardEntity.getStatus();
        this.title = boardEntity.getTitle();
        this.writerId = boardEntity.getWriterId();
        this.writeDatetime = writeDatetime;
        this.contents = boardEntity.getContents();
        this.comment = boardEntity.getComment();
    }

    public static ResponseEntity<GetBoardResponseDto> success(BoardEntity boardEntity) throws Exception {
        GetBoardResponseDto responseBody = new GetBoardResponseDto(boardEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
