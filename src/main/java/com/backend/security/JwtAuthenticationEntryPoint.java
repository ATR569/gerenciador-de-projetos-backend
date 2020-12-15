package com.backend.security;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backend.exceptions.ApiExceptionObject;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        ApiExceptionObject exception = new ApiExceptionObject(401, "O usuario nao esta autenticado", "Nao autorizado");
        
        response.getOutputStream().println(exception.toString());
        // response.getOutputStream().println("{ \"error\": \"" + authException.getMessage() + "\" }");
    }

}