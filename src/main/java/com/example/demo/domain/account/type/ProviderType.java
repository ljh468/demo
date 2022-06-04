package com.example.demo.domain.account.type;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum ProviderType {
    LOCAL("일반", "L"),
    KAKAO("카카오", "K");
    private String name;
    private String value;
}
