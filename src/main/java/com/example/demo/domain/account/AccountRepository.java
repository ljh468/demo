package com.example.demo.domain.account;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // Username을 기준으로해서 권한정보와 유저정보를 함께 가져옴
    @EntityGraph(attributePaths = "authorities")
    Optional<Account> findOneWithAuthoritiesByEmail(String email);

    // 이메일을 기준으로  유정정보를 가져옴
    Optional<Account> findByEmail(String email);
}
