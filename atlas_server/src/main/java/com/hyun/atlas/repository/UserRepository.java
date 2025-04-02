package com.hyun.atlas.repository;

import com.hyun.atlas.dto.LoginResponseDTO;
import com.hyun.atlas.dto.UserLoginDTO;
import com.hyun.atlas.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = """
        SELECT
            A.USR_NM,
            A.COMPANY_CD,
            A.REG_ID_NO,
            null,
            A.USR_GRP_CD,
            A.AUT_GRP_CD,
            A.DEPT_CD,
            B.CODE_NM,
            A.BRN_CD,
            A.BOUND_CLS,
            A.USR_CLS,
            A.CUST_ID,
            A.CUST_DEPT_CD,
            null,
            null,
            A.LANGUAGE,
            C.PK_PRINTER,
            C.CI_PRINTER,
            C.CMR_PRINTER,
            C.CIM_PRINTER,
            A.COMPANY_CD,
            A.VEND_CD,
            A.PORT_CD,
            nvl(A.EXP_DT, to_char(sysdate, 'yyyymmdd')),
            null
        FROM TC_USER_MST A
            LEFT OUTER JOIN (SELECT COMPANY_CD, CODE_CD, CODE_NM FROM TC_CODE_D WHERE CLSS_CD = 'D1') B
                                             ON A.DEPT_CD = B.CODE_CD AND A.COMPANY_CD = B.COMPANY_CD
            LEFT OUTER JOIN TC_PRINTER_M C ON A.USR_CD = C.USR_CD
        WHERE A.USR_CD = :code
        """)
    LoginResponseDTO getLoginResponseDTOByCode(String code);

    @Query(nativeQuery = true, value = """
        SELECT nvl(use_ldap,'N'), FN_DECRYPT(USR_PWD), TRY_CNT, EXP_DT, FN_DECRYPT(MAIL_NO), USR_NM
                FROM TC_USER_MST
                WHERE USR_CD  = :code
                  AND (use_yn IS NULL OR use_yn like 'Y');
        """)
    Optional<UserLoginDTO> getUserLoginDTOByCode(String code);

    User findByCode(String code);
}
