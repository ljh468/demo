package com.example.demo.domain.account;

import com.example.demo.domain.account.input.SignUpInput;
import com.example.demo.domain.account.type.ProviderType;
import com.example.demo.domain.auth.Token;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountMutation implements GraphQLMutationResolver {

    private static final String DUMMYPASSWORD = "eat-place-spring-boot";
    private final AccountService accountService;

    @Autowired
    public AccountMutation(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 유저 회원가입 Mutaion
     */
    public Token signUp(SignUpInput input) {
        try {
            Account account = accountService.registerAccount(input);
            String username;
            String password;
            if (ProviderType.LOCAL.equals(input.getProviderType())) {
                username = account.getUsername();
                password = input.getPassword();
            } else {
                username = account.getUsername();
                password = DUMMYPASSWORD;
            }
            // authentication 객체를 리턴
            UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(username, password);
            // security context에 authentication 생성
            SecurityContextHolder.getContext().setAuthentication(credentials);
            Account currentAccount = accountService.getCurrentAccount();
            return token;
        } catch (Exception e){
            throw new RuntimeException();
        }
    }
}
