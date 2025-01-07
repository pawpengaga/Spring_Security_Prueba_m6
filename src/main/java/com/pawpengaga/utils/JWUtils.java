package com.pawpengaga.utils;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWUtils {

  // Vamos a generar el metodo de creacion del token, y el metodo de validación del token

  @Value("${security.key}")
  private String privatekey;

  @Value("${security.user}")
  private String userGenerator;

  // Creacion de token
  public String createToken(Authentication authentication){
    Algorithm algorithm = Algorithm.HMAC256(privatekey);
    String username = authentication.getPrincipal().toString();

    // Esto devuelve los permisos separados por comas
    String authorities = authentication.getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));

    String jwtToken = JWT.create()
      .withIssuer(userGenerator)
      .withSubject(username)
      .withClaim("authorities", authorities)
      .withIssuedAt(new Date())
      .withExpiresAt(new Date(System.currentTimeMillis() + 1800000)) // Desde que se crea, media hora mas
      .withJWTId(UUID.randomUUID().toString())
      .withNotBefore(new Date(System.currentTimeMillis()))
      .sign(algorithm);
    
    return jwtToken;

  }

}
