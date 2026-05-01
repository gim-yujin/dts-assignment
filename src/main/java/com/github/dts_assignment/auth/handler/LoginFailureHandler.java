package com.github.dts_assignment.auth.handler;

import com.github.dts_assignment.auth.entity.LoginHistory;
import com.github.dts_assignment.auth.repository.LoginHistoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final LoginHistoryRepository loginHistoryRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        String username = request.getParameter("username");

        loginHistoryRepository.save(LoginHistory.builder()
                .user(null)
                .username(username != null ? username : "")
                .success(false)
                .ipAddress(request.getRemoteAddr())
                .build());

        response.sendRedirect(request.getContextPath() + "/login?error");
    }
}
