package com.rbac.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI rbacOpenAPI() {
    return new OpenAPI()
      .info(new Info()
        .title("RBAC Permission Management API")
        .description("Spring Boot RBAC backend project API documentation")
        .version("v1.0.0")
        .contact(new Contact()
          .name("luosheep")
          .url("https://github.com/luosheep2020"))
        .license(new License()
          .name("MIT")))
      .externalDocs(new ExternalDocumentation()
        .description("GitHub Repository")
        .url("https://github.com/luosheep2020/springboot-rbac-system"));
  }
}
