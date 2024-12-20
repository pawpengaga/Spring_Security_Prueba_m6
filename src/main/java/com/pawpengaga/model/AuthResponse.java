package com.pawpengaga.model;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class AuthResponse {

  // ESTA CLASE NO DEBERÍA ESTAR EN MODEL

  @NotBlank
  private String correo;
  @NotBlank
  private List<String> roles;


}
