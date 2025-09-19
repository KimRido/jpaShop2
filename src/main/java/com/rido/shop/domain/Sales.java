package com.rido.shop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
public class Sales extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_id")
    private Long id;
    private String itemName;
    private Integer price;
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    //생성메서드
    public static Sales createSales(Member member, Item item, Integer count, Integer price) {
        Sales sales = new Sales();
        sales.setItemName(item.getTitle());
        sales.setPrice(price);
        sales.setCount(count);
        sales.setPrice(price);
        sales.setMember(member);
        sales.setItem(item);

        return sales;
    }
}
