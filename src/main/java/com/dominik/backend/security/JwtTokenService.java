package com.dominik.backend.security;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by dominik on 12.04.2017.
 *
 * Klasa odpowiadająca za tworzenie i walidację tokenów JWT
 *
 */


public class JwtTokenService {

    private static String key;
    private static String prefix;
    private static String header;
    private static String issuer;

    static {
        key = "dhfsajkfh7yr7823qyrhjcasbaewyfiu";
        prefix = "Bearer";
        header = "Authorization";
        issuer = "planit-backend.com:8888";
    }

    public static void addAuthentication(HttpServletResponse response, String login, String authorities) {

        Claims claims = Jwts.claims().setSubject(login);

        claims.setIssuer(issuer);

        claims.put("roles", authorities);

        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        response.addHeader(header, prefix + " " + jwtToken);
    }

    public static Authentication performAuthentication(HttpServletRequest request) throws ServletException {

        String jwtToken = request.getHeader(header);

        if (jwtToken != null) {

            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken.replace(prefix, ""));

            String iss = claims.getBody().getIssuer();

            if (!iss.equals(issuer)) {
                throw new ServletException("Issuer niezgodny");
            }

            String login = claims.getBody().getSubject();

            String authorities = claims.getBody().get("roles", String.class);

            List<GrantedAuthority> roles = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

            return login != null ? new UsernamePasswordAuthenticationToken(login, null, roles) : null;
        }

        return null;
    }
}
