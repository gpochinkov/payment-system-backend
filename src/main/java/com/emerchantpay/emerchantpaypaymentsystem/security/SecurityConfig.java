package com.emerchantpay.emerchantpaypaymentsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    http
        .csrf().disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtConfig))
        .addFilterAfter(new JwtTokenVerifierFilter(jwtConfig),
                        JwtUsernamePasswordAuthenticationFilter.class)
        .authorizeRequests()
        //            .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
        //            .antMatchers("/api/**").hasRole(STUDENT.name())
        .anyRequest()
        .authenticated();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
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
