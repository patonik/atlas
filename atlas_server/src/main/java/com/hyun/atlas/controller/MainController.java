package com.hyun.atlas.controller;

import com.hyun.atlas.dto.MainPageDTO;
import com.hyun.atlas.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final MainService mainService;

    @GetMapping("/api/main")
    public List<MainPageDTO> getMainPage(@RequestHeader(name = "Authorization") String token) {
        log.info("getMainPage with token {}", token);
        return mainService.getMainPage(token);
    }
}
