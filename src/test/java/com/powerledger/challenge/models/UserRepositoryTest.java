package com.powerledger.challenge.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void test_findByUsername(){
        User user = new User(1L, "test", "123");
        userRepository.save(user);
        Optional<User> actual = userRepository.findByUsername("test");
        assertTrue(actual.isPresent());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }
}