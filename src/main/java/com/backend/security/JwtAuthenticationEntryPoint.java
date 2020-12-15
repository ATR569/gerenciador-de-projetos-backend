package com.backend.security;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backend.exceptions.ApiException;
import com.backend.exceptions.ApiExceptionObject;
import com.backend.exceptions.EmptyBodyException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        ApiExceptionObject exception = new ApiExceptionObject(401, "O usuario nao esta autenticado", "Nao autorizado");
        
        response.getOutputStream().println(exception.toString());
    }

    @ExceptionHandler(AuthenticationException.class)
    public void forbidden(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws ApiException {
            throw new EmptyBodyException();
    }


}