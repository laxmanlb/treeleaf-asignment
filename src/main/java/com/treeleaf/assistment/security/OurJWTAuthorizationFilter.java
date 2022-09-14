/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.treeleaf.assistment.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.treeleaf.assistment.repository.UserRepository;
import static com.treeleaf.assistment.security.SecurityConstants.HEADER_STRING;
import static com.treeleaf.assistment.security.SecurityConstants.SECRET;
import static com.treeleaf.assistment.security.SecurityConstants.TOKEN_PREFIX;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 *
 * @author Laxman Baniya
 */
public class OurJWTAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    public OurJWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            // parse the token.

            var signingKey = SECRET.getBytes();
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""));
            String user = decodedJWT.getSubject();
            System.out.println("USER obtained from TOKEN:" + user);
            Claim claim = decodedJWT.getClaim("rol");
            List<String> roleList = claim.asList(String.class);
            List<GrantedAuthority> roleGrantedList = new ArrayList<>();
            for (String role : roleList) {
                System.out.println("Role is" + role);
                roleGrantedList.add(new SimpleGrantedAuthority(role));
            }

            if (user != null) {
                // new arraylist means authorities

                return new UsernamePasswordAuthenticationToken(user, null, roleGrantedList);
            }

            return null;
        }

        return null;
    }
}
