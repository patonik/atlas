package com.hyun.atlas.service;

import com.hyun.atlas.dto.LoginRequestDTO;
import com.hyun.atlas.dto.LoginResponseDTO;
import com.hyun.atlas.dto.UserLoginDTO;
import com.hyun.atlas.entity.Driver;
import com.hyun.atlas.repository.DriverRepository;
import com.hyun.atlas.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    @PersistenceContext
    private EntityManager em;
    @Transactional
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO response = new LoginResponseDTO();
        String userCode = loginRequestDTO.getUserCode();
        String systemSite = loginRequestDTO.getSystemSite();

        //driver authentication
        if (systemSite.equals("DRTD")) {
            String license = driverRepository.getLicenseByLicense(userCode);
            if (license == null) {
                response.setErrorCode("N");
                response.setErrorMessage(
                    "FAIL TO LOGIN GOALS SYSTEM.. \\n PLEASE LOGIN AGAIN, AFTER VERIFY YOUR ID AND PASSWORD!!");
                return response;
            }
            Driver driver = driverRepository.findByLicense(userCode);
            response.setUserName(driver.getSurname() + " " + driver.getFirstName() + " " + driver.getMiddleName());
            response.setCompanyCode("HMCIS");
            response.setRegistrationId(license);
            response.setUserGroupCode("EXT03");
            response.setPermissionGroupCode("2006");
            response.setDepartmentCode("DRV");
            response.setDepartmentName("driver");
            response.setCustomerId("HMCIS");
            response.setLanguage("1039");
            response.setCompanyCode("HMCIS");
            response.setVendorCode(driver.getVendorCode());
            return response;
        }
        // user not found
        Optional<UserLoginDTO> dtoOptional = userRepository.getUserLoginDTOByCode(userCode);
        if (dtoOptional.isEmpty()) {
            response.setErrorCode("N");
            response.setErrorMessage("User not found");
            return response;
        }
        UserLoginDTO userDetails = dtoOptional.get();
        String userPassword = loginRequestDTO.getUserPassword();
        if (userPassword.equals("remind_pwd")) {
            boolean sent = sendPasswordReminder(userCode, userDetails.getDecryptedPassword(), userDetails.getEmail(),
                userDetails.getUserName(), userDetails.getIsLdap());
            response.setErrorCode("N");
            if (!sent) {
                response.setErrorMessage("FAIL TO RESEND PASSWORD - no email!!");
                return response;
            }
            response.setErrorMessage("Password sent to mailbox");
            return response;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate expirationDate = LocalDate.parse(userDetails.getExpirationDate(), formatter);
        LocalDate now = LocalDate.now();
        if (expirationDate.isBefore(now) || expirationDate.equals(now)) {
            response.setErrorCode("N");
            response.setErrorMessage("FAILED TO LOGIN - password expired!");
            return response;
        }
        if (userDetails.getAttempts() > 9) {
            response.setErrorCode("N");
            response.setErrorMessage("FAILED TO LOGIN - login attempts exceeded!");
            return response;
        }
        if (userDetails.getIsLdap().equals("Y")) {
            // TODO implement ldap authentication
        }
        if (!userDetails.getDecryptedPassword().equals(userPassword)) {
            response.setErrorCode("N");
            response.setErrorMessage("Invalid credentials");
            return response;
        }
        return userRepository.getLoginResponseDTOByCode(userCode);
    }

    private boolean sendPasswordReminder(String code, String pass, String email, String name, String isLdap) {
        String emailText;
        if (email == null) {
            //cannot send pass
            return false;
        }
        StoredProcedureQuery query = em.createStoredProcedureQuery("MAIL_PKG.create_req_with_file");
        query.registerStoredProcedureParameter("receiver", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("receiver_cc", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("sender", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("subject", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("text", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("addtext", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FileName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Pdate", LocalDate.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Priority", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("pass", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("add_attach", Object.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("attach_type", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("receiver_bcc", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("dba_dir", String.class, ParameterMode.IN);
        if (isLdap.equals("Y")) {
            emailText = """
                    Добрый день!<br><br> Для входа в систему используте логин/пароль для входа в Windows.<br><br>Инструкцию по первоначальной настройке можете найти во вложенном файле.<br><br>
                    По техническим вопросам функционирования программы АТЛАС пишите на адрес <a href="mailto:it.moscow@glovisrus.com">it.moscow@glovisrus.com</a>
                """;
        } else {
            emailText = """
                Добрый день!<br><br>Напомниаем Ваши учетные данные в системе Атлас.<br>Система доступна по следующему веб адресу <a href="https://atlas.glovisrus.com">https://atlas.glovisrus.com</a>. <BR>
                Имя пользователя: ' || %s || '<BR>Логин: ' || %s || '<BR>Пароль: ' || %s || '<br><br>Инструкцию по первоначальной настройке можете найти во вложенном файле.<br><br>
                    По техническим вопросам функционирования программы АТЛАС пишите на адрес <a href="mailto:it.moscow@glovisrus.com">it.moscow@glovisrus.com</a> <BR>
                    Пароль можно сменить во вкладке System -> Password change
                """.formatted(name, code, pass);
        }
        query.setParameter("receiver", email);
        query.setParameter("receiver_cc", "it.moscow@glovisrus.com");
        query.setParameter("sender", "noreply@glovisrus.com");
        query.setParameter("subject", "Учетная запись Атлас");
        query.setParameter("text", emailText);
        query.setParameter("addtext", null);
        query.setParameter("FileName", "FirstStartATLAS.docx");
        query.setParameter("Pdate", LocalDate.now());
        query.setParameter("Priority", 2);
        query.setParameter("pass", null);
        query.setParameter("add_attach", null);
        query.setParameter("attach_type", 5);

        query.execute();
        return true;
    }

}
