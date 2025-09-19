package com.rido.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.rido.shop.domain.Item;
import com.rido.shop.domain.Member;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class OrderListDto {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer count;

    private String username;
    private String displayName;

    private String imgPath;

    @QueryProjection
    public OrderListDto(Long id, String itemName, Integer price, Integer count, String username, String displayName, String imgPath) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.count = count;
        this.username = username;
        this.displayName = displayName;
        this.imgPath = imgPath;
    }
}
