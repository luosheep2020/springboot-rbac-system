package com.rbac.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {

  private final StringRedisTemplate stringRedisTemplate;

  public void set(String key, String value, long timeout, TimeUnit unit) {
    stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
  }

  public String get(String key) {
    return stringRedisTemplate.opsForValue().get(key);
  }

  public Boolean delete(String key) {
    return stringRedisTemplate.delete(key);
  }

  public Boolean hasKey(String key) {
    return stringRedisTemplate.hasKey(key);
  }
}
