package com.rdrg.back.entity;

import com.rdrg.back.dto.request.board.PostBoardRequestDto;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "board")
@Table(name = "board")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer receptionNumber;
    private Boolean status;
    private String title;
    private String contents;
    private String writerId;
    private String writerDatetime;
    private String comment;
    // private String uploadFile;//업로드 파일을 불러오기 위해서는 어떻게 해야하나!

    public BoardEntity (PostBoardRequestDto dto, String userId) {
        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String writerDatetime = simpleDateFormat.format(now);

        this.status = false;
        this.title = dto.getTitle();
        this.contents = dto.getContents();
        this.writerId = userId;
        this.writerDatetime = writerDatetime;
    }
}
