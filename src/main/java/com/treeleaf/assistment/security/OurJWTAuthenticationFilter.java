/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.treeleaf.assistment.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.treeleaf.assistment.entity.User;
import static com.treeleaf.assistment.security.SecurityConstants.EXPIRATION_TIME;
import static com.treeleaf.assistment.security.SecurityConstants.SECRET;
import com.treeleaf.assistment.service.CustomUserDetails;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author Laxman Baniya
 */
public class OurJWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter 
{
    
    private AuthenticationManager authenticationManager;

    public OurJWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl("/login"); 
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUserName(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        
       CustomUserDetails customUser=(CustomUserDetails)auth.getPrincipal();
       
        List <String> roles= new ArrayList<>();
        int count=1;
        for(GrantedAuthority ga:customUser.getAuthorities())
        {
            System.out.println("ROLES:"+count+" "+ga.getAuthority());
            roles.add(ga.getAuthority());
            count++;
        }
       
        String token = JWT.create()
                .withSubject(customUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("rol",roles)  //THIS IS IMPORTANT AS WE ARE ASSINGING ROLES
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
        res.getWriter().write(token);
        res.getWriter().flush();
    }

}