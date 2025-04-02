package com.hyun.atlas.repository;

import com.hyun.atlas.entity.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class UserRepositoryTest {
    private final UserRepository userRepository;

    @Test
    public void getUser() {
        User user = userRepository.findByCode("0900561");
        assertEquals("0900561", user.getCode());
    }

}