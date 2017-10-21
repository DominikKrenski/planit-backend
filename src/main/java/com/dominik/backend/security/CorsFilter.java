package com.dominik.backend.security;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dominik on 26.04.2017.
 */

@Component
public class CorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        response.setHeader("ACCESS-CONTROL-ALLOW-ORIGIN", "*");
        //response.setHeader("ACCESS-CONTROL-ALLOW-ORIGIN", "http://vps400801.ovh.net");
        response.setHeader("ACCESS-CONTROL-ALLOW-METHODS", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("ACCESS-CONTROL-MAX-AGE", "3600");
        response.setHeader("ACCESS-CONTROL-ALLOW-HEADERS", "Origin, Authorization, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        response.setHeader("ACCESS-CONTROL-EXPOSE-HEADERS", "Authorization, Access-Control-Allow-Headers, Access-Control-Allow-Methods, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");

        if (request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            filterChain.doFilter(request, response);
        }
    }
}
