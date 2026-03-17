package com.rbac.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long expiration;

  private SecretKey secretKey;

  @PostConstruct
  public void init() {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * 生成 token
   */
  public String generateToken(Long userId, String username) {
    Date now = new Date();
    Date expireDate = new Date(now.getTime() + expiration);

    return Jwts.builder()
      .subject(username)
      .claim("userId", userId)
      .issuedAt(now)
      .expiration(expireDate)
      .signWith(secretKey)
      .compact();
  }

  /**
   * 解析 token
   */
  public Claims parseToken(String token) {
    return Jwts.parser()
      .verifyWith(secretKey)
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }

  /**
   * 获取 username
   */
  public String getUsername(String token) {
    return parseToken(token).getSubject();
  }

  /**
   * 获取 userId
   */
  public Long getUserId(String token) {
    Object userId = parseToken(token).get("userId");
    return Long.valueOf(String.valueOf(userId));
  }

  /**
   * 判断 token 是否过期
   */
  public boolean isTokenExpired(String token) {
    Date expirationDate = parseToken(token).getExpiration();
    return expirationDate.before(new Date());
  }
}
