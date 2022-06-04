package com.example.demo.domain;

import com.example.demo.domain.account.Account;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue
    private Long teamSeq;

    private String teamName;

    private String teamGeneratorId;

    private String userYn;

    @OneToMany(mappedBy = "team")
    private List<Account> accounts = new ArrayList<>();
}
