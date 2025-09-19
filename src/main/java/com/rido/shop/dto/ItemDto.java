package com.rido.shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class ItemDto {
    private Long id;

    @NotBlank
    private String title;
    @NotNull
    private Integer price;

    private String imgPath;

    //기본생성자가 없어서 thymeleaf에서 매핑할때 오류남
    public ItemDto() {
    }

    public ItemDto(String title, Integer price, String imgPath) {
        this.title = title;
        this.price = price;
        this.imgPath = imgPath;
    }
}
