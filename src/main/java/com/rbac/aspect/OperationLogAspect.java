package com.rbac.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbac.entity.LoginLog;
import com.rbac.entity.OperationLog;
import com.rbac.repository.OperationLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class OperationLogAspect {
  private final OperationLogRepository operationLogRepository;
  private final HttpServletRequest request;
  private final ObjectMapper objectMapper;

  @Around("execution(* com.rbac.controller..*(..))")
  public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
    OperationLog log = new OperationLog();
    String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    log.setUsername(username);
    log.setMethod(joinPoint.getSignature().getName());
    log.setUri(request.getRequestURI());
    log.setParams(objectMapper.writeValueAsString(joinPoint.getArgs()));
    log.setCreateTime(LocalDateTime.now());
    try {
      Object result = joinPoint.proceed();
      log.setStatus(1);
      return result;
    } catch (Exception e) {
      log.setStatus(0);
      throw e;
    } finally {
      operationLogRepository.save(log);
    }
  }

}
