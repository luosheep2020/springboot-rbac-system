package com.rbac.exception;

public class BusinessException extends RuntimeException {

  private final Integer code;

  public BusinessException(Integer code, String message) {
    super(message);
    this.code = code;
  }

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.code = errorCode.getCode();
  }

  public Integer getCode() {
    return code;
  }
}
