package com.kimo.anime1.anime.security;

import java.io.IOException;

import com.kimo.anime1.anime.comment.ErrorCode;
import com.kimo.anime1.anime.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kimo.anime1.anime.repository.TokenRepostiory;
import com.kimo.anime1.anime.security.jwt.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * 认证过滤器
 * @author  kimo
 */
@Component
@RequiredArgsConstructor
public class AuthenticateFilter extends OncePerRequestFilter {
    @Autowired
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepostiory tokenRepostiory;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
            throws ServletException, IOException {
        if(request.getServletPath().contains("/users")){
            filterChain.doFilter(request, response);
            return;
        }


        final String header = request.getHeader("Authorization");

        final String jwt;
        final String userEmail;
        if(header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);

            return;
        }
        jwt = header.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            Boolean isTokenValid = tokenRepostiory.findByToken(jwt).map(token -> !token.isExpired() && !token.isRevoked()).orElse(false);
            if(jwtService.isTokenValid(jwt, userDetails) && isTokenValid){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );
                authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }else{
                throw new BusinessException(ErrorCode.TOKEN_INVALID);
            }
        }
        filterChain.doFilter(request, response);


    }
    
}
