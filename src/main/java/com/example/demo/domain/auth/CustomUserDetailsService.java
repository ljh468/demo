package com.example.demo.domain.auth;

import com.example.demo.domain.account.Account;
import com.example.demo.domain.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Autowired
    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {
        return accountRepository.findOneWithAuthoritiesByEmail(email)
                .map(account -> createUser(email, account))
                .orElseThrow(() -> new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다."));
    }
    private org.springframework.security.core.userdetails.User createUser(String email, Account account) {
        if (!account.getUseYn().equals("Y")) {
            throw new RuntimeException(email + " -> 활성화되어 있지 않습니다.");
        }
        // 유저가 활성화상태라면
        List<GrantedAuthority> grantedAuthorities = account.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
        // 유저이름, 유저패스워드, 권한정보가지고 User객체를 반환함
        return new org.springframework.security.core.userdetails.User(account.getEmail(),
                account.getPassword(),
                grantedAuthorities);
    }
}
