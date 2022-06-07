package com.example.demo.domain.account;

import com.example.demo.domain.account.input.SignUpInput;
import com.example.demo.domain.account.type.ProviderType;
import com.example.demo.domain.auth.Token;
import com.example.demo.domain.auth.TokenProvider;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountMutation implements GraphQLMutationResolver {
    private static final String DUMMYPASSWORD = "eat-place-spring-boot";

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    public AccountMutation(AccountService accountService,
                           AccountRepository accountRepository,
                           TokenProvider tokenProvider,
                           AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Autowired


    /**
     * 유저 회원가입 Mutaion
     */
    public Token signUp(SignUpInput input){
        try {
            if(accountRepository.findOneWithAuthoritiesByEmail(input.getEmail()).orElse(null) != null){
                throw new RuntimeException("이미 가입되어 있는 이메일입니다.");
            }
            Account account = accountService.registerAccount(input);
            String userEmail;
            String password;
            if (ProviderType.LOCAL.equals(input.getProviderType())) {
                userEmail = account.getEmail();
                password = input.getPassword();
            } else {
                userEmail = account.getEmail();
                password = DUMMYPASSWORD;
            }
            // authentication 객체를 리턴
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userEmail, password);

            // authenticationToken을 이용해서 authenticate메서드가 실행이 될때 loadUserByUsername메서드가 실행됨
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            // security context에 authentication 생성
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Token token = tokenProvider.createToken(authentication);

            return null;
        } catch (Exception e){
            throw new RuntimeException();
        }
    }
}
