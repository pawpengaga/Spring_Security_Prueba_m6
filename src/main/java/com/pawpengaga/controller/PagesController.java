package com.pawpengaga.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PagesController {

  @GetMapping
  public String root(){
    return "login";
  }

  @GetMapping("/404")
  public String cuatroCeroCuatro(){
    return "404";
  }

  @GetMapping("/401")
  public String cuatroCeroUno(){
    return "401";
  }

}
