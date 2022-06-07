package com.example.demo.domain.account;

import com.example.demo.domain.account.input.SignUpInput;
import com.example.demo.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * 유저 회원가입
     */
    @Transactional
    public Account registerAccount(SignUpInput input) {
        Account account = Account.builder()
                .username(input.getUsername())
                .password(input.getPassword())
                .age(input.getAge())
                .joinDt(LocalDate.now())
                .build();
        return accountRepository.save(account);
    }

    /**
     * SecurityContext에 저장된 유저정보만 가져옴
     */
    @Transactional(readOnly = true)
    public Optional<Account> getCurrentUsername() {
        return SecurityUtil.getCurrentUsername().flatMap(accountRepository::findOneWithAuthoritiesByEmail);
    }

    public Account getCurrentAccount() {
        AtomicReference<String> username = new AtomicReference<>();
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(authentication -> {
                    username.set(authentication.getName());
                    return username.get();
                })
                .flatMap(accountRepository::findByEmail)
                .orElseThrow(() -> new EmailNotFoundException(username.get()));
    }

    /**
     * 이메일 중복체크
     */
    public Boolean emailCheck(String email) {
        Optional<Account>Account = accountRepository.findByEmail(email);
        // 계정이 있으면 true, 없으면 false
        return Account.isPresent();
    }
}
