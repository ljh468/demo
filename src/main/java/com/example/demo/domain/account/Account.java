package com.example.demo.domain.account;

import com.example.demo.domain.Team;
import com.example.demo.domain.account.type.ProviderType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private ProviderType providerType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    private String birth;

    private Long age;

    private LocalDate joinDt;

    @ManyToOne
    @JoinColumn(name = "teamSeq")
    private Team team;

}
