package com.rido.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NoticeDto {

    private String title;
    private LocalDateTime createdDate;

}
