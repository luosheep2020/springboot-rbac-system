package com.rbac.dto;


import lombok.Data;

@Data
public class CreateUserRequest {

  private String username;

  private String password;

  private Integer enabled;
}
