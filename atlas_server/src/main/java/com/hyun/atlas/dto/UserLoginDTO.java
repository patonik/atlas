package com.hyun.atlas.dto;

import lombok.Data;

@Data
public class UserLoginDTO {
    private String isLdap;
    private String decryptedPassword;
    private Short attempts;
    private String expirationDate;
    private String email;
    private String userName;
}
