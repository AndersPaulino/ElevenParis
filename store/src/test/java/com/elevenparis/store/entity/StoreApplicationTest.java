package com.elevenparis.store.entity;

import com.elevenparis.store.StoreApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class StoreApplicationTest {
    @Test
    void mainMethodShouldNotThrowException() {
        assertDoesNotThrow(() -> StoreApplication.main(new String[]{}));
    }
}
