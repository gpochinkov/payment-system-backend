package com.emerchantpay.emerchantpaypaymentsystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
@SuppressWarnings("unused")
public class SwaggerConfig {

  @SuppressWarnings("unused")
  @Bean
  public Docket swaggerConfigBean() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        //        .paths(PathSelectors.ant("/api/**"))
        .paths(PathSelectors.regex("(?!/error).+"))
        .build();
  }

}
