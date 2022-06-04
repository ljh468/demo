package com.example.demo.domain.account;

import java.text.MessageFormat;

public class EmailNotFoundException extends RuntimeException{
    private final String identifier;

    public EmailNotFoundException(String identifier) {
        this.identifier = identifier;
    }

    // 다수의 데이터를 같은 양식으로 출력할 때 주로 사용한다.
    @Override
    public String getMessage(){
        return MessageFormat.format("User with Identifier ''{0}'' isn''t available", identifier);
    }

}
