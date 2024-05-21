package com.rdrg.back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "upload")
@Table(name = "upload")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer fileNumber;
    private Integer linkBoardNumber;
    private String url;

    public UploadEntity(Integer linkBoardNumber, String url) {
        this.linkBoardNumber = linkBoardNumber;
        this.url = url;
    }
}
