package com.rdrg.back.dto.request.board;

import java.util.List;

import com.rdrg.back.common.object.BoardFileItem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostBoardRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String contents;
    private List<BoardFileItem> fileList;
}
