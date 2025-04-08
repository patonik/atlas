package com.hyun.atlas.dto;

public interface UserLoginDTO {
    String getIsLdap();
    String getDecryptedPassword();
    int getAttempts();
    String getExpirationDate();
    String getEmail();
    String getUserName();
}
