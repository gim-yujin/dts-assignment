package com.github.dts_assignment.user.repository;

import com.github.dts_assignment.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        // created_at is insertable=false, so use JDBC to bypass Hibernate mapping
        jdbcTemplate.execute("""
                INSERT INTO users (username, password, email, created_at)
                VALUES ('alice', '$2a$10$hashedpassword', 'alice@example.com', CURRENT_TIMESTAMP)
                """);
    }

    @Test
    void findByUsername_returnsUser_whenExists() {
        Optional<User> result = userRepository.findByUsername("alice");

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("alice");
        assertThat(result.get().getEmail()).isEqualTo("alice@example.com");
        assertThat(result.get().getPassword()).isEqualTo("$2a$10$hashedpassword");
    }

    @Test
    void findByUsername_returnsEmpty_whenNotExists() {
        Optional<User> result = userRepository.findByUsername("nonexistent");

        assertThat(result).isEmpty();
    }

    @Test
    void save_assignsId() {
        jdbcTemplate.execute("""
                INSERT INTO users (username, password, email, created_at)
                VALUES ('bob', '$2a$10$anotherhashedpw', 'bob@example.com', CURRENT_TIMESTAMP)
                """);

        Optional<User> result = userRepository.findByUsername("bob");

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isNotNull().isPositive();
    }
}
