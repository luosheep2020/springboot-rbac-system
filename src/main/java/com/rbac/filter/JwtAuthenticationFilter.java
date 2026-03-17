package com.rbac.filter;

import com.rbac.service.CustomUserDetailsService;
import com.rbac.util.JwtUtil;
import com.rbac.util.RedisUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final RedisUtil redisUtil;
  private final CustomUserDetailsService customUserDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = authHeader.substring(7);

    try {
      Claims claims = jwtUtil.parseToken(token);
      String username = claims.getSubject();
      Long userId = jwtUtil.getUserId(token);

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        String redisKey = "login:token:" + userId;
        String redisToken = redisUtil.get(redisKey);

        if (redisToken != null && redisToken.equals(token) && !jwtUtil.isTokenExpired(token)) {

          List<GrantedAuthority> authorities =
            customUserDetailsService.loadUserAuthorities(username);

          UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
              username,
              null,
              authorities
            );

          authentication.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
          );

          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }

    } catch (Exception e) {
      SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(request, response);
  }
}
