package com.rbac.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

  SUCCESS(200, "success"),

  BAD_REQUEST(400, "bad request"),
  UNAUTHORIZED(401, "unauthorized"),
  FORBIDDEN(403, "forbidden"),
  NOT_FOUND(404, "not found"),

  USERNAME_ALREADY_EXISTS(4001, "username already exists"),
  USER_NOT_FOUND(4002, "user not found"),
  PASSWORD_ERROR(4003, "password error"),
  ROLE_NOT_FOUND(4004, "role not found"),
  ROLE_CODE_ALREADY_EXISTS(4005, "role code already exists"),
  PERMISSION_NOT_FOUND(4006, "permission not found"),
  PERMISSION_CODE_ALREADY_EXISTS(4007, "permission code already exists"),
  SYSTEM_ERROR(5000, "system error");

  private final Integer code;
  private final String message;

  ErrorCode(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

}
