package com.example.demo.domain.account.input;

import com.example.demo.domain.account.type.ProviderType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@RequiredArgsConstructor
@Getter
public class SignUpInput {
    private final ProviderType providerType;
    private final String username;
    private final String password;
    private final String email;
    private final String birth;
    private final Long age;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

