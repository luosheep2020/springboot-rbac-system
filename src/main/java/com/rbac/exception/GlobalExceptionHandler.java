package com.rbac.exception;

import com.rbac.common.Response;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 处理业务异常
   */
  @ExceptionHandler(BusinessException.class)
  public Response<?> handleBusinessException(BusinessException e) {
    return Response.fail(e.getCode(), e.getMessage());
  }

  /**
   * 处理参数校验异常
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Response<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    String message = Objects.requireNonNull(e.getBindingResult()
        .getFieldError())
      .getDefaultMessage();
    return Response.fail(ErrorCode.BAD_REQUEST.getCode(), message);
  }

  /**
   * 处理其他系统异常
   */
  @ExceptionHandler(Exception.class)
  public Response<?> handleException(Exception e) {
    e.printStackTrace();
    return Response.fail(ErrorCode.SYSTEM_ERROR.getCode(), ErrorCode.SYSTEM_ERROR.getMessage());
  }
}
