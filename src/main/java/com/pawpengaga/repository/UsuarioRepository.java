package com.pawpengaga.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pawpengaga.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

  // Cuando es algo es opcional puede conseguirlo como que no
  Optional<Usuario> findByEmail(String email);

}
