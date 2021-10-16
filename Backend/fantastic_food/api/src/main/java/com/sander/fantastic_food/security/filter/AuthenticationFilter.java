package com.sander.fantastic_food.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sander.fantastic_food.UserProfile;
import com.sander.fantastic_food.security.service.UserProfilePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@PropertySource("classpath:application.properties")
public class AuthenticationFilter  extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserProfile profile = objectMapper.readValue(request.getInputStream(), UserProfile.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    profile.getEmail(),
                    profile.getPasswordHash(), new UserProfilePrincipal(profile).getAuthorities()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        UserProfilePrincipal profilePrincipal = (UserProfilePrincipal) authResult.getPrincipal();
        UserProfile profile = profilePrincipal.getProfile();
        Date expiryDate = new Date(System.currentTimeMillis() + 900_000);

        String token = JWT.create().withSubject(profile.getEmail())
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC512("i love mobile development and harry potter".getBytes(StandardCharsets.UTF_8)));

        String body = token;

        response.getWriter().write(body);
        response.getWriter().flush();
    }
}
