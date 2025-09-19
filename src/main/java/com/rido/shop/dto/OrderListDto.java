package com.rido.shop.dto;

import com.rido.shop.domain.Item;
import com.rido.shop.domain.Member;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderListDto {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer count;

    private String username;
    private String displayName;

    private String imgPath;


/*    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;*/

}
