package com.pawpengaga.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {

  @NotBlank
  private String correo;
  @NotBlank
  private String clave;

}
