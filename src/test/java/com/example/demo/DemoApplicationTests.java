package com.example.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Disabled
@SpringBootTest
@ActiveProfiles("test") // 👈 Carrega o arquivo application-test.properties automaticamente
class DemoApplicationTests {

    @Test
    void contextLoads() {

    }
}