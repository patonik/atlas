package com.hyun.atlas.controller;

import com.hyun.atlas.dto.LoginRequestDTO;
import com.hyun.atlas.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        log.info("Login request: {}", loginRequestDTO);
        String result = authService.login(loginRequestDTO);
        if (result.startsWith("Bearer ")) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, result);
            log.info(result);
            return new ResponseEntity<>(null, headers, HttpStatus.OK);
        } else {
            log.error(result);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }
    }
}
