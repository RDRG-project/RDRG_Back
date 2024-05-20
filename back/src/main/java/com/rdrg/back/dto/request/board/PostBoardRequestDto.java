package com.rdrg.back.dto.request.board;

import jakarta.validation.constraints.NotBlank;
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
    private String Url;
}
