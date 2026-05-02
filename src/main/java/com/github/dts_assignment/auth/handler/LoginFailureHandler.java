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
        // Authentication 객체가 없는 실패 흐름이므로 username 은 form parameter 에서 직접 얻는다.
        String username = request.getParameter("username");

        loginHistoryRepository.save(LoginHistory.failure(username, request.getRemoteAddr()));

        response.sendRedirect(request.getContextPath() + "/login?error");
    }
}
