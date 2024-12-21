package com.pawpengaga.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PagesController {

  @GetMapping
  public String root(){
    return "index";
  }

  @GetMapping("404")
  public String cuatroCeroCuatro(){
    return "pages/404";
  }

  @GetMapping("401")
  public String cuatroCeroUno(){
    return "pages/401";
  }

  /* PAGINAS /pages */

    // Existe un concepto aplicado a cada controlador llamado Pre Autorizar, es una alternativa poco practica al controlador

    @GetMapping("pages/public")
    public String hola(){
      return "pages/publico";
    }
  
    @GetMapping("pages/config")
    public String config(){
      return "pages/config";
    }
  
    @GetMapping("pages/privado")
    public String holaPrivado(){
      return "pages/privado";
    }

}
