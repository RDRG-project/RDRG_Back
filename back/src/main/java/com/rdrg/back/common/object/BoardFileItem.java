package com.rdrg.back.common.object;

import com.rdrg.back.entity.UploadEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardFileItem {
    private String url;
    private String originalFileName;

    public BoardFileItem(UploadEntity uploadEntity){
        this.url = uploadEntity.getUrl();
        this.originalFileName = uploadEntity.getImgOriginalName();
    }
}
