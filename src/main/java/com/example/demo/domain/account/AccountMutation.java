package com.example.demo.domain.account;

import com.example.demo.domain.account.input.SignInInput;
import com.example.demo.domain.account.input.SignUpInput;
import com.example.demo.domain.account.type.GenderType;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Component
public class AccountMutation implements GraphQLMutationResolver {
    private static final String DUMMYPASSWORD = "eat-place-spring-boot";

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountMutation(AccountService accountService,
                           AccountRepository accountRepository,
                           TokenProvider tokenProvider,
                           AuthenticationManagerBuilder authenticationManagerBuilder,
                           PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 유저 회원가입 Mutaion
     */
    public Token signUp(SignUpInput input){
        try {
            if(accountRepository.findOneWithAuthoritiesByEmail(input.getEmail()).orElse(null) != null){
                throw new RuntimeException("이미 가입되어 있는 이메일입니다.");
            }
            String userEmail;
            String password;
            if (ProviderType.LOCAL.equals(input.getProviderType())) {
                userEmail = input.getEmail();
                password = input.getPassword();
            } else {
                userEmail = input.getEmail();
                password = DUMMYPASSWORD;
            }
            // 유저정보가 없으면 권한정보를 만듬 (권한은 ROLE_USER 하나만 가진다.)
            Authority authority = Authority.builder()
                    .authorityName("ROLE_USER")
                    .build();

            // 권한정보와 유저정보를 담아서 저장
            Account account = Account.builder()
                    .email(userEmail)
                    .password(passwordEncoder.encode(password))
                    .nickName(input.getNickname())
                    .age(input.getAge())
                    .birth(input.getBirth())
                    .Gender(GenderType.valueOf(input.getGender()))
                    .authorities(Collections.singleton(authority))
                    .joinDt(LocalDate.now())
                    .useYn("Y")
                    .build();

            // 데이터 저장
            accountService.registerAccount(account);

            // authentication 객체를 리턴
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userEmail, password);

            // authenticationToken을 이용해서 authenticate메서드가 실행이 될때 loadUserByUsername메서드가 실행됨
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            // security context에 authentication 생성
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return tokenProvider.createToken(authentication);
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    public Token signIn(SignInInput input){
        try{
            String userEmail;
            String password;

            if (ProviderType.LOCAL.equals(input.getProviderType())) {
                userEmail = input.getEmail();
                password = input.getPassword();
            } else {
                userEmail = input.getEmail();
                password = DUMMYPASSWORD;
            }

            // authentication 객체를 리턴
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userEmail, password);

            // authenticationToken을 이용해서 authenticate메서드가 실행이 될때 loadUserByUsername메서드가 실행됨
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            // security context에 authentication 생성
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return tokenProvider.createToken(authentication);
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
