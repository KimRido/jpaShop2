package com.rido.shop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String username;
    @Column(length = 1000)
    private String content;
    private Long parentId;

    public Comment(String username, String content, Long parentId) {
        this.username = username;
        this.content = content;
        this.parentId = parentId;
    }

    public Comment() {
    }
}
