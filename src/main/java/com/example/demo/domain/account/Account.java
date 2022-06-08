package com.example.demo.domain.account;

import com.example.demo.domain.Team;
import com.example.demo.domain.account.type.AccountRoleType;
import com.example.demo.domain.account.type.GenderType;
import com.example.demo.domain.account.type.ProviderType;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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

    private String email;

    private String password;

    private String nickName;

    private String birth;

    private GenderType Gender;

    private Long age;

    private LocalDate joinDt;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccountRoleType role;

    // 카카오 회원번호
    private String identifier;

    private String profileImageUrl;
    @ManyToOne
    @JoinColumn(name = "teamSeq")
    private Team team;

}
