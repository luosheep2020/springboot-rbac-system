package com.rbac.controller;

import com.rbac.common.Response;
import com.rbac.dto.LoginRequest;
import com.rbac.service.AuthService;
import com.rbac.vo.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public Response<LoginResponse> login(@RequestBody LoginRequest request) {
    return Response.success(authService.login(request));
  }

  @PostMapping("/logout/{userId}")
  public Response<Void> logout(@PathVariable Long userId) {
    authService.logout(userId);
    return Response.<Void>success(null);
  }
}
