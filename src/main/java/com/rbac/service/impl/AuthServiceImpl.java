package com.rbac.service.impl;

import com.rbac.dto.LoginRequest;
import com.rbac.entity.User;
import com.rbac.exception.BusinessException;
import com.rbac.exception.ErrorCode;
import com.rbac.repository.UserRepository;
import com.rbac.service.AuthService;
import com.rbac.util.JwtUtil;
import com.rbac.util.RedisUtil;
import com.rbac.vo.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final RedisUtil redisUtil;

  @Override
  public LoginResponse login(LoginRequest request) {
    User user = userRepository
      .findByUsernameAndDeletedAndEnabled(request.getUsername(), 0, 1)
      .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

    boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
    if (!matches) {
      throw new BusinessException(ErrorCode.PASSWORD_ERROR);
    }
    String token= jwtUtil.generateToken(user.getId(),user.getUsername());
    String redisKey="login:token"+user.getId();
    redisUtil.set(redisKey,token,24, TimeUnit.HOURS);
    return new LoginResponse()
      .setUserId(user.getId())
      .setUsername(user.getUsername())
      .setEnabled(user.getEnabled())
      .setToken(token)
      .setTokenType("Bearer")
      .setMessage("login success");
  }

  @Override
  public void logout(Long userId) {
    String redisKey = "login:token:" + userId;
    redisUtil.delete(redisKey);
  }
}
