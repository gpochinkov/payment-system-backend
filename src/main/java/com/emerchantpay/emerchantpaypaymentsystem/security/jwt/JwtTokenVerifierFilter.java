package com.emerchantpay.emerchantpaypaymentsystem.security.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtTokenVerifierFilter extends OncePerRequestFilter {

  private JwtConfig jwtConfig;

  public JwtTokenVerifierFilter(JwtConfig jwtConfig) {
    this.jwtConfig = jwtConfig;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

    String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

    if (authorizationHeader == null || authorizationHeader.equals("")
        || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");

    try {
      Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtConfig.getSecretKeyAsSecretKey())
                                  .parseClaimsJws(token);
      Claims body = claimsJws.getBody();
      String username = body.getSubject();

      Authentication authentication = new UsernamePasswordAuthenticationToken(
          username,
          null,
          null
      );

      SecurityContextHolder.getContext().setAuthentication(authentication);

    } catch (JwtException e) {
      throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
    }

    filterChain.doFilter(request, response);
  }
}
