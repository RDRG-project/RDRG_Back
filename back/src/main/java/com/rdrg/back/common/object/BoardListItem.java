package com.rdrg.back.common.object;

import java.util.List;
import java.util.ArrayList;

import com.rdrg.back.common.util.ChangeDateFormatUtil;
import com.rdrg.back.entity.BoardEntity;

import lombok.Getter;

@Getter
public class BoardListItem {
    private Integer receptionNumber;
    private Boolean status;
    private String title;
    private String writerId;
    private String writeDatetime;

    private BoardListItem(BoardEntity boardEntity) throws Exception {
        String writeDatetime = ChangeDateFormatUtil.changeYYMMDD(boardEntity.getWriteDatetime());

        String writerId = boardEntity.getWriterId();
        writerId = writerId.substring(0, 1) + "*".repeat(writerId.length() - 1);

        this.receptionNumber = boardEntity.getReceptionNumber();
        this.status = boardEntity.getStatus();
        this.title = boardEntity.getTitle();
        this.writerId = writerId;
        this.writeDatetime = writeDatetime;
    }

    public static List<BoardListItem> getList(List<BoardEntity> boardEntities) throws Exception {
        List<BoardListItem> boardList = new ArrayList<>();

        for (BoardEntity boardEntity : boardEntities) {
            BoardListItem boardListItem = new BoardListItem(boardEntity);
            boardList.add(boardListItem);
        }
        return boardList;
    }
}


