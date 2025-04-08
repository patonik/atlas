package com.hyun.atlas.service;

import com.hyun.atlas.dto.MainPageDTO;
import com.hyun.atlas.repository.MainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {
    private final MainRepository mainRepository;
    private final JwtUtil jwtUtil;

    public List<MainPageDTO> getMainPage(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String userCode = jwtUtil.extractUserCode(token);
        return mainRepository.findAllByUserCode(userCode);
    }
}
