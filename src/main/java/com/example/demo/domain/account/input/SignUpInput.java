package com.example.demo.domain.account.input;

import com.example.demo.domain.account.type.ProviderType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@RequiredArgsConstructor
@Getter
public class SignUpInput {
    /**
     * 로그인 수단
     */
    private final ProviderType providerType;
    /**
     * 유저 이름
     */
    private final String username;
    /**
     * 유저 패스워드
     */
    private final String password;
    /**
     * 유저 이메일
     */
    private final String email;
    /**
     * 유저 생년월일
     */
    private final String birth;
    /**
     * 유저 나이
     */
    private final Long age;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

