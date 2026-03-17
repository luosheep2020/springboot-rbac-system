package com.rbac.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginResponse {

  private Long userId;

  private String username;

  private Integer enabled;

  private String token;

  private String tokenType;

  private String message;
}
