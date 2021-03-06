package com.example.demo.domain.account;

import java.text.MessageFormat;

public class EmailNotFoundException extends RuntimeException{
    private final String identifier;

    public EmailNotFoundException(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getMessage(){
        // 다수의 데이터를 같은 양식으로 출력할 때 주로 사용한다.
        return MessageFormat.format("회원 고유번호 ''{0}'' 사용이 불가능합니다.", identifier);
    }

}
