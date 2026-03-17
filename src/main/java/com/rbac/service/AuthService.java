package com.rbac.service;

import com.rbac.dto.LoginRequest;
import com.rbac.vo.LoginResponse;

public interface AuthService {

  LoginResponse login(LoginRequest request);
  void logout(Long userId);
}
