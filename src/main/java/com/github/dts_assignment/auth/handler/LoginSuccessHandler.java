package com.github.dts_assignment.auth.handler;

import com.github.dts_assignment.auth.entity.LoginHistory;
import com.github.dts_assignment.auth.repository.LoginHistoryRepository;
import com.github.dts_assignment.user.entity.User;
import com.github.dts_assignment.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final LoginHistoryRepository loginHistoryRepository;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);

        loginHistoryRepository.save(LoginHistory.success(user, username, request.getRemoteAddr()));

        response.sendRedirect(request.getContextPath() + "/");
    }
}
