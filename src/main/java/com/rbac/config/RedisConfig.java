package com.rbac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

@Configuration
public class RedisConfig {

  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {

    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);

    // key 序列化
    RedisSerializer<String> stringSerializer = new StringRedisSerializer();

    // value JSON 序列化
    RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer();

    redisTemplate.setKeySerializer(stringSerializer);
    redisTemplate.setValueSerializer(jsonSerializer);

    redisTemplate.setHashKeySerializer(stringSerializer);
    redisTemplate.setHashValueSerializer(jsonSerializer);

    redisTemplate.afterPropertiesSet();

    return redisTemplate;
  }
}
