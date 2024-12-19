package com.pawpengaga.controller;

// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class TestController {

  @GetMapping("/public")
  public String hola(){
    return "Hola endpoint publico!";
  }

  @GetMapping("/privado")
  public String holaPrivado(){
    return "Hola! Deberías haber pasado la seguridad para ver esto...";
  }

}
