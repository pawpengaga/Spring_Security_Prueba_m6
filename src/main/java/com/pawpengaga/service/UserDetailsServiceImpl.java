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

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // El nombre de usuario es correo
    Usuario userEntity = userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado..."));
    
    List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

    userEntity.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole().name())))); // => Resultado: 'ROLE_ADMIN' por ejemplo
    
    userEntity.getRoles().stream().flatMap(role -> role.getPermisos().stream())
      .forEach(permiso -> authorityList.add(new SimpleGrantedAuthority(permiso.getName())));

    return new User(
        userEntity.getEmail(),
        userEntity.getPassword(),
        userEntity.isEnabled(),
        userEntity.isAccountNoExpired(),
        userEntity.isCredentialNoExpired(),
        userEntity.isAccountNoLocked(),
        authorityList
      );
  }

}
