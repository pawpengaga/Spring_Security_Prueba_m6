package com.pawpengaga.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pawpengaga.model.Usuario;
import com.pawpengaga.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UsuarioRepository userRepo;

  /* Esto afecta las Authorities del SECURITY CONTEXT HOLDER */

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // El nombre de usuario es correo

    // Buscamos desde el repo o mandamos una excepción (Que también podríamos mandar un nulo)
    Usuario userEntity = userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado..."));
    

    List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

    // Llenamos la lista authorities con los roles los permisos
    userEntity.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole().name())))); // => Resultado: 'ROLE_ADMIN' por ejemplo
    
    userEntity.getRoles().stream().flatMap(role -> role.getPermisos().stream())
      .forEach(permiso -> authorityList.add(new SimpleGrantedAuthority(permiso.getName())));

    return new User(
        userEntity.getEmail(),
        userEntity.getPassword(),
        userEntity.isEnabled(),
        userEntity.isAccountNoExpired(),
        userEntity.isAccountNoLocked(),
        userEntity.isCredentialNoExpired(),
        authorityList
      );
  }

}
