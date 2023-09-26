package com.elevenparis.store.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AbstractEntityTest {

    private AbstractEntityTestImpl entity;

    @BeforeEach
    void setUp() {
        entity = new AbstractEntityTestImpl();
    }

    @Test
    void prePersistShouldSetRegistroAndAtivo() {
        entity.prePersist();

        assertNotNull(entity.getRegistro());
        assertTrue(entity.isAtivo());
    }

    @Test
    void preUpdateShouldSetAtualizar() {
        entity.preUpdate();

        assertNotNull(entity.getAtualizar());
    }

    private static class AbstractEntityTestImpl extends AbstractEntity {

        @PrePersist
        void customPrePersist() {
            super.prePersist();
        }

        @PreUpdate
        void customPreUpdate() {
            super.preUpdate();
        }
    }
}
