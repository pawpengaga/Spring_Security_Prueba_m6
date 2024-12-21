package com.pawpengaga.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pawpengaga.model.AuthResponse;
import com.pawpengaga.model.LoginRequest;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @GetMapping("/login")
  public String login(){
    return "pages/login";
  }

  @PostMapping("/login")
  public AuthResponse loginProcess(@Valid @RequestBody LoginRequest request){

    // Este es el lugar primario de la autenticacion

    // ESTA LINEA ES LA QUE VALIDA
    Authentication authentication = authenticationManager
      .authenticate(new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getClave()));
    
    // El objecto tenga la validación o no, lo guardamos en el context holder
    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    // El usuario puede tener mas de un rol, los guardamos todos aqui
    // El rol es una clase que contiene las authorities
    List<String> roles = userDetails.getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority).toList();

    // Generamos una validacion
    AuthResponse authResponse = new AuthResponse(userDetails.getUsername(), roles);

    // Me va a retornar que pueden ser mas utiles que simplemente generar un String
    return authResponse;

  }

}
