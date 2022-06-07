package com.example.demo.domain.account;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Team {
    /**
     * 팀 고유번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamSeq;

    /**
     * 팀 이름
     */
    private String teamId;

    /**
     * 팀 생성자
     */
    private String teamGenaratorId;

    /**
     * 팀 사용여부     */
    private String useYn;

    /**
     * Account와 양방향 매핑을 위해 Team을 연관 매핑함
     * 연관관계의 주인이 아님 , 조회만 가능
     */
    @OneToMany(mappedBy = "team")
    private List<Account> accounts = new ArrayList<>();
}
