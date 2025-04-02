package com.hyun.atlas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String userName;
    private String registrationId;
    private String email;
    private String userGroupCode;
    private String permissionGroupCode;
    private String departmentCode;
    private String departmentName;
    private String branchCode;
    private String boundClass;
    private String userClass;
    private String customerId;
    private String customerDepartmentCode;
    private String telephoneNumber;
    private String faxNumber;
    private String language;
    private String pkPrinter;
    private String ciPrinter;
    private String cmrPrinter;
    private String cimPrinter;
    private String errorCode;
    private String errorMessage;
    private String companyCode;
    private String vendorCode;
    private String portCode;
    private String sessionId;
}
