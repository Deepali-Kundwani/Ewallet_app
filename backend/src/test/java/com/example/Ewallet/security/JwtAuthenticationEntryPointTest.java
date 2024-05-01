package com.example.Ewallet.security;

import com.example.Ewallet.security.JwtAuthenticationEntryPoint;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

@SpringBootTest
public class JwtAuthenticationEntryPointTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private AuthenticationException authException;
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        authenticationEntryPoint = new JwtAuthenticationEntryPoint();
        authException = new AuthenticationException("Access Denied") {
        };
    }

    @Test
    public void commenceShouldSetStatusAndWriteErrorMessage() throws IOException, ServletException {
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authenticationEntryPoint.commence(request, response, authException);

        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(writer, times(1)).println("Access Denied !! " + authException.getMessage());
    }
}
