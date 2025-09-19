package com.rido.shop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
//(JpaAuditing) 3. 각 Entity에서 BaseEntity 상속받기
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String displayName;

    @OneToMany(mappedBy = "member")
    private List<Sales> sales = new ArrayList<>();

    public Member(String username, String password, String displayName) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
    }
}
