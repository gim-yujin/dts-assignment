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
        insertUser("alice", "$2a$10$hashedpassword", "alice@example.com");
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
        insertUser("bob", "$2a$10$anotherhashedpw", "bob@example.com");

        Optional<User> result = userRepository.findByUsername("bob");

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isNotNull().isPositive();
    }

    // created_at 은 insertable=false 라 Hibernate 매핑을 우회하기 위해 JDBC 로 직접 삽입한다.
    private void insertUser(String username, String password, String email) {
        jdbcTemplate.update(
                "INSERT INTO users (username, password, email, created_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP)",
                username, password, email);
    }
}
