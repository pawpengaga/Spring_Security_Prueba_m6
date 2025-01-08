package com.pawpengaga.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
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

  // Vamos a validar el token!
  public DecodedJWT validateToken(String token){
    try {
      
      // Usamos el mismo algoritmo de codificación
      Algorithm algorithm = Algorithm.HMAC256(privatekey);

      // Construimos un verificador al que le pasamos tanto el algoritmo con el usuario
      JWTVerifier verifier = JWT.require(algorithm)
        .withIssuer(userGenerator)
        .build();

      // Usamos ese verificador para decodificar
      DecodedJWT decoded = verifier.verify(token);

      return decoded;

    } catch (JWTVerificationException e) {
      throw new JWTVerificationException("Token inválido: Solicitudes no autorizadas.");
    }
  }

  /* ********************************* METODOS UTILES ********************************* */

  // Obtener el nombre de usuarios
  public String extractUserName(DecodedJWT decoded){
    return decoded.getSubject().toLowerCase();
  }

  // Obtener un claim especifico del payload
  public Claim getSpecifiedClaim(DecodedJWT decoded, String claimName){
    return decoded.getClaim(claimName);
  }

  // Un metodo que nos devuelva todos los claims
  public Map<String, Claim> getAllClaims(DecodedJWT decoded){
    return decoded.getClaims();
  }

}
