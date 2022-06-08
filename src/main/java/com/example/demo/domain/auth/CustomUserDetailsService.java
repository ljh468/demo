package com.example.demo.domain.auth;

import com.example.demo.domain.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Autowired
    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public JWTUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findOneWithAuthoritiesByEmail(email)
                .map(account -> getUserDetails(account, ));
    }
}
