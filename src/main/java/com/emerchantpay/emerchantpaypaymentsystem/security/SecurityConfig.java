package com.emerchantpay.emerchantpaypaymentsystem.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import com.emerchantpay.emerchantpaypaymentsystem.security.jwt.JwtConfig;
import com.emerchantpay.emerchantpaypaymentsystem.security.jwt.JwtTokenVerifierFilter;
import com.emerchantpay.emerchantpaypaymentsystem.security.jwt.JwtUsernamePasswordAuthenticationFilter;
import com.emerchantpay.emerchantpaypaymentsystem.service.EmerchantpayUserService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final PasswordEncoder passwordEncoder;
  private final EmerchantpayUserService emerchantpayUserService;
  private final JwtConfig jwtConfig;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
    corsConfiguration.setAllowedMethods(List.of("*"));
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.setExposedHeaders(List.of("Authorization"));

    http
        .csrf().disable()
        //        .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .cors().configurationSource(request -> corsConfiguration)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtConfig))
        .addFilterAfter(new JwtTokenVerifierFilter(jwtConfig),
                        JwtUsernamePasswordAuthenticationFilter.class)
        .authorizeRequests().antMatchers("/login",
                                         "/v2/api-docs",
                                         "/v3/api-docs",
                                         "/swagger-ui/index.html",
                                         "/swagger-ui/**",
                                         "/swagger-resources/**").permitAll()
        //            .antMatchers("/api/**").hasRole()
        .anyRequest().authenticated()
    ;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(daoAuthenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder);
    provider.setUserDetailsService(emerchantpayUserService);
    return provider;
  }
}
