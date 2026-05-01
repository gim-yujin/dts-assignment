package com.github.dts_assignment.auth.controller;

import com.github.dts_assignment.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    void loginPage_returns200AndContainsForm() throws Exception {
        MvcResult result = mockMvc.perform(get("/login")).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString(StandardCharsets.UTF_8))
                .contains("로그인")
                .contains("name=\"username\"")
                .contains("name=\"password\"");
    }

    @Test
    void homePage_redirectsToLogin_whenUnauthenticated() throws Exception {
        MvcResult result = mockMvc.perform(get("/")).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(302);
        assertThat(result.getResponse().getHeader("Location")).contains("/login");
    }

    @Test
    @WithMockUser(username = "alice")
    void homePage_returns200AndContainsUsername_whenAuthenticated() throws Exception {
        MvcResult result = mockMvc.perform(get("/")).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString(StandardCharsets.UTF_8))
                .contains("alice")
                .contains("로그인 성공");
    }

    @Test
    void logoutSuccessPage_returns200() throws Exception {
        MvcResult result = mockMvc.perform(get("/logout-success")).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString(StandardCharsets.UTF_8))
                .contains("로그아웃");
    }
}
