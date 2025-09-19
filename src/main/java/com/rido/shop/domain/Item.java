package com.rido.shop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(columnList = "title", name = "indexItem"))
public class Item extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    private String title;
    private Integer price;
    private String imgPath;
    private Integer count;

    @OneToOne(mappedBy = "item", fetch = FetchType.LAZY)
    private Sales sales;

    public Item(String title, Integer price) {
        this.title = title;
        this.price = price;
    }

    public Item(String title, Integer price, String imgPath) {
        this.title = title;
        this.price = price;
        this.imgPath = imgPath;
    }
}
