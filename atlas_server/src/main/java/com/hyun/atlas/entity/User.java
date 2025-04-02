package com.hyun.atlas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "TC_USER_MST")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "USR_CD", length = 30)
    private String code;

    @Column(name = "USR_PWD", length = 100)
    private String password;

    @Column(name = "USR_NM", length = 60)
    private String userName;

    @Column(name = "REG_ID_NO", length = 14)
    private String registrationIdNumber;

    @Column(name = "ZIP_CD", length = 7)
    private String zipCode;

    @Nationalized
    @Column(name = "ADDR", length = 200)
    private String address;

    @Column(name = "TEL_NO", length = 150)
    private String telephoneNumber;

    @Column(name = "HP_NO", length = 150)
    private String hpNo;

    @Nationalized
    @Column(name = "MAIL_NO", length = 500)
    private String email;

    @Nationalized
    @Column(name = "USR_GRP_CD", length = 10)
    private String userGroupCode;

    @Column(name = "AUT_GRP_CD", length = 10)
    private String permissionGroupCode;

    @Column(name = "DEPT_CD", length = 7)
    private String departmentCode;

    @Column(name = "BRN_CD", length = 3)
    private String brnCd;

    @Column(name = "USR_CLS", length = 1)
    private String userClass;

    @Column(name = "CUST_ID", length = 15)
    private String customerId;

    @Column(name = "CUST_DEPT_CD", length = 7)
    private String customerDepartmentCode;

    @Column(name = "INP_USR", length = 30)
    private String inputUser;

    @ColumnDefault("sysdate")
    @Column(name = "INP_YMD")
    private LocalDate inputDate;

    @Column(name = "UPD_USR", length = 30)
    private String updateUser;

    @Column(name = "UPD_YMD")
    private LocalDate updateDate;

    @Column(name = "USR_SEQ")
    private Short userSequence;

    @Column(name = "FAX_NO_NAT", length = 100)
    private String faxNumberNat;

    @Column(name = "FAX_NO_AREA", length = 100)
    private String faxNumberArea;

    @Column(name = "FAX_NO_DTL", length = 100)
    private String faxNumberDetail;

    @Nationalized
    @Column(name = "USR_NM_ENG", length = 40)
    private String userNameEng;

    @Column(name = "BOUND_CLS", length = 1)
    private String boundClass;

    @Column(name = "LANGUAGE", length = 4)
    private String language;

    @Column(name = "COMPANY_CD", length = 5)
    private String companyCode;

    @Column(name = "FAXNO", length = 50)
    private String faxNumber;

    @Column(name = "VEND_CD", length = 20)
    private String vendorCode;

    @Column(name = "PORT_CD", length = 5)
    private String portCode;

    @Column(name = "USR_NM_RU", length = 100)
    private String userNameRu;

    @Column(name = "PATT_DT", length = 10)
    private String pattDt;

    @Column(name = "POSIT", length = 100)
    private String posit;

    @Column(name = "EXP_DT", length = 10)
    private String expirationDate;

    @ColumnDefault("'Y'")
    @Column(name = "USE_YN", nullable = false, length = 1)
    private String isInUse;

    @Column(name = "TRY_CNT", nullable = false)
    private Short loginAttempts;

    @Column(name = "USE_LDAP", nullable = false, length = 1)
    private String isLdap;

    @Column(name = "ADD_PORTS", length = 100)
    private String addPorts;

    @Column(name = "USR_NM_SUR", length = 30)
    private String surname;

    @Column(name = "USR_NM_NAME", length = 30)
    private String name;

    @Column(name = "USR_NM_PAT", length = 30)
    private String usrNmPat;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(code, user.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return "User{" +
            "code='" + code + '\'' +
            ", password='" + password + '\'' +
            ", userName='" + userName + '\'' +
            ", registrationIdNumber='" + registrationIdNumber + '\'' +
            ", zipCode='" + zipCode + '\'' +
            ", address='" + address + '\'' +
            ", telephoneNumber='" + telephoneNumber + '\'' +
            ", hpNo='" + hpNo + '\'' +
            ", mailNumber='" + email + '\'' +
            ", userGroupCode='" + userGroupCode + '\'' +
            ", permissionGroupCode='" + permissionGroupCode + '\'' +
            ", departmentCode='" + departmentCode + '\'' +
            ", brnCd='" + brnCd + '\'' +
            ", userClass='" + userClass + '\'' +
            ", customerId='" + customerId + '\'' +
            ", customerDepartmentCode='" + customerDepartmentCode + '\'' +
            ", inputUser='" + inputUser + '\'' +
            ", inputDate=" + inputDate +
            ", updateUser='" + updateUser + '\'' +
            ", updateDate=" + updateDate +
            ", userSequence=" + userSequence +
            ", faxNumberNat='" + faxNumberNat + '\'' +
            ", faxNumberArea='" + faxNumberArea + '\'' +
            ", faxNumberDetail='" + faxNumberDetail + '\'' +
            ", userNameEng='" + userNameEng + '\'' +
            ", boundClass='" + boundClass + '\'' +
            ", language='" + language + '\'' +
            ", companyCode='" + companyCode + '\'' +
            ", faxNumber='" + faxNumber + '\'' +
            ", vendorCode='" + vendorCode + '\'' +
            ", portCode='" + portCode + '\'' +
            ", userNameRu='" + userNameRu + '\'' +
            ", pattDt='" + pattDt + '\'' +
            ", posit='" + posit + '\'' +
            ", expirationDate='" + expirationDate + '\'' +
            ", isInUse=" + isInUse +
            ", loginAttempts=" + loginAttempts +
            ", isLdap=" + isLdap +
            ", addPorts='" + addPorts + '\'' +
            ", surname='" + surname + '\'' +
            ", name='" + name + '\'' +
            ", usrNmPat='" + usrNmPat + '\'' +
            '}';
    }
}