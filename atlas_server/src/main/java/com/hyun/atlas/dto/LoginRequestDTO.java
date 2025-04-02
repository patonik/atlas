package com.hyun.atlas.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoginRequestDTO {
    private String userCode;
    private String userPassword;
    private LocalDate loginDate;
    private String ipAddress;
    private String systemType;
    private String systemSite;
}
