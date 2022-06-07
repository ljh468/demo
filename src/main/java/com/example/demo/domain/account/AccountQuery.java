package com.example.demo.domain.account;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountQuery implements GraphQLQueryResolver {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    /**
     * 스프링 시큐리티에서 현재 유저정보 가져오기
     */
    public Account getCurrentAccount(){
        // orElse는 null이던말던 항상 불립니다.
        // orElseGet은 null일 때만 불립니다.
        Account currentAccount = accountService.getCurrentUsername().orElseGet(Account::new);
        log.debug("currentAccount : " + currentAccount);
        return currentAccount;
    }
    /**
     * 입력한 이메일로 Account 중복 조회 (없으면 false)
     */
    public Boolean emailCheck(String email){
        return accountService.emailCheck(email);
    }
}
