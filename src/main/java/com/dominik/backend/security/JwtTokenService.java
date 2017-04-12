package com.dominik.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

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

    static {
        key = "dhfsajkfh7yr7823qyrhjcasbaewyfiu";
        prefix = "Bearer";
        header = "Authorization";
    }

    public static void addAuthentication(HttpServletResponse response, String login, String authorities) {

        Claims claims = Jwts.claims().setSubject(login);

        claims.put("roles", authorities);

        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        response.addHeader(header, prefix + " " + jwtToken);
    }

    public static Authentication performAuthentication(HttpServletRequest request) {

        String jwtToken = request.getHeader(header);

        if (jwtToken != null) {
            String login = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwtToken.replace(prefix, ""))
                    .getBody()
                    .getSubject();

            String authorities = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwtToken.replace(prefix, ""))
                    .getBody()
                    .get("roles", String.class);

            List<GrantedAuthority> roles = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

            return login != null ? new UsernamePasswordAuthenticationToken(login, null, roles) : null;
        }

        return null;
    }
}
