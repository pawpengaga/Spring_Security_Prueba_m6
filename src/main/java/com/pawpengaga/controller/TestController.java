package com.pawpengaga.controller;

import org.springframework.stereotype.Controller;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/auth")
public class TestController {

  // Existe un concepto aplicado a cada controlador llamado Pre Autorizar, es una alternativa poco practica al controlador

  @GetMapping("/public")
  public String hola(){
    return "publico";
  }

  @GetMapping("/config")
  public String config(){
    return "config";
  }

  @GetMapping("/privado")
  public String holaPrivado(){
    return "privado";
  }



}
