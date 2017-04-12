package com.dominik.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by dominik on 12.04.2017.
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtLoginFilter.class);

    public JwtLoginFilter(String url, AuthenticationManager authenticationManager) {

        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException
    {
        AccountCredentials credentials = new ObjectMapper().readValue(request.getReader(), AccountCredentials.class);

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getLogin(), credentials.getPassword())
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication)
        throws IOException, ServletException
    {
        Set<String> auth = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        String authorities = auth.iterator().next();

        authorities = authorities.replace("[", "").replace("]", "");

        if (authorities.contains(",")) {
            authorities = authorities.replace(" ", "");
        }

        logger.info("============UTWORZONY CIĄG ZNAKÓW Z ROLAMI==============");
        logger.info(authorities);

        JwtTokenService.addAuthentication(response, authentication.getName(), authorities);
    }
}
