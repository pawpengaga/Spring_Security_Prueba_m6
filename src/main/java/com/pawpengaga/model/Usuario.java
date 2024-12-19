package com.pawpengaga.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder // Otro metodo de lombok
@Data
@Entity
@Table(name = "usuarios") // Nombre personalizado para la tabla
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(unique = true)
  private String email;

  private String password;

  // ATRIBUTOS REQUERIDOS POR SPRING SECURITY
  @Column(name = "is_expired")
  private boolean isEnabled;
  
  @Column(name = "account_no_expired")
  private boolean accountNoExpired;
  
  @Column(name = "account_no_locked")
  private boolean accountNoLocked;
  
  @Column(name = "credential_no_expired")
  private boolean credentialNoExpired;

}
