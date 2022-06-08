package com.example.demo.domain.auth;

import com.example.demo.domain.account.Account;
import com.example.demo.domain.account.type.AccountRoleType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Builder

public class JWTUserDetails implements UserDetails {

    private final Account account;

    private final String accessToken;

    /**
     * 계정이 갖고 있는 권한 목록을 리턴
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        AccountRoleType accountRole = account.getRole();
        String authority = accountRole.getAuthority();

        SimpleGrantedAuthority simpleAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
