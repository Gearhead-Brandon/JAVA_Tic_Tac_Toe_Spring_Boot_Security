package game.security.tictactoe.configuration.security.filter;

import feign.FeignException;
import game.security.tictactoe.domain.service.auth.AuthClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFilter extends GenericFilterBean {

    private final AuthClient authClient;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        try {
            ResponseEntity<UUID> response = authClient.login();

            final UUID userId = response.getBody();

            Authentication authentication = new UsernamePasswordAuthenticationToken(response.getBody(), null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            httpRequest.setAttribute("User-UUID", userId);

            filterChain.doFilter(httpRequest, servletResponse);

        }catch (FeignException e) {
            handleUnauthorized(e, httpResponse);
        }catch (Exception e) {
            log.error("Error in authorization filter", e);
        }
    }

    private void handleUnauthorized(FeignException e, HttpServletResponse httpResponse) throws IOException {
        if (e.status() >= 500) {
            log.error("Authorization service unavailable", e);
            httpResponse.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            httpResponse.getWriter().write("{\"error\": \"Authorization service unavailable\"}");
            return;
        }

        httpResponse.setStatus(e.status()); // Возвращаем код ошибки

        e.responseHeaders().forEach((key, value) -> {
            String headerValue = String.join(",", value);
            httpResponse.addHeader(key, headerValue);
        });

        httpResponse.setContentType("application/json");

        if (e.responseBody().isPresent()) {
            String responseBody = new String(e.responseBody().get().array(), StandardCharsets.UTF_8);

            httpResponse.getWriter().write(responseBody); // Отправляем тело ответа
            httpResponse.getWriter().flush();
        }
    }
}
