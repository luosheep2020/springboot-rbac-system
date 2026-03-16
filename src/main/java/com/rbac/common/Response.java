package com.rbac.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Response<T> implements Serializable {

  private Integer code;
  private String message;
  private T data;

  public static <T> Response<T> success(T data) {
    Response<T> response = new Response<>();
    response.setCode(200);
    response.setMessage("success");
    response.setData(data);
    return response;
  }

  public static <T> Response<T> success(String message, T data) {
    Response<T> response = new Response<>();
    response.setCode(200);
    response.setMessage(message);
    response.setData(data);
    return response;
  }

  public static <T> Response<T> fail(Integer code, String message) {
    Response<T> response = new Response<>();
    response.setCode(code);
    response.setMessage(message);
    response.setData(null);
    return response;
  }

  public static <T> Response<T> fail(String message) {
    return fail(500, message);
  }
}
