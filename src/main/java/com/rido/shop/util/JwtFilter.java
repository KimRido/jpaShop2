package com.rido.shop.util;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//(JWT) 필터등록 컨트롤러 실행전에 실행된다.
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            //쿠키가 비어있을 경우 다음 필터로 넘어간다.
            filterChain.doFilter(request, response);
            return;
        }

        String jwtCookie = "";
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("jwt")) {
                jwtCookie = cookie.getValue();
            }
        }

        System.out.println("jwtCookie = " + jwtCookie);

        Claims claims;
        try {
            claims = JwtUtil.extractToken(jwtCookie);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        String[] arr = claims.get("authorities").toString().split(",");
        List<SimpleGrantedAuthority> authorities = Arrays.stream(arr).map(a -> new SimpleGrantedAuthority(a)).toList();

        User user = new User(
                claims.get("username").toString(),
                "none",
                authorities);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
