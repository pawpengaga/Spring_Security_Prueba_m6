package com.pawpengaga.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pawpengaga.dto.AuthLoginRequest;
import com.pawpengaga.dto.AuthResponse;
// import com.pawpengaga.model.AuthResponse;
import com.pawpengaga.model.Usuario;
import com.pawpengaga.repository.UsuarioRepository;
import com.pawpengaga.utils.JWUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UsuarioRepository userRepo;

  @Autowired
  JWUtils jwtUtils;

  @Autowired
  PasswordEncoder passwordEncoder;

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

  /* **************************************** JWT UPDATE **************************************** */

  public AuthResponse loginUser(AuthLoginRequest userRequest){
    
    String username = userRequest.username();
    String password = userRequest.password();

    Authentication auth = authenticate(username, password); // Esto es otro metodo mas
    SecurityContextHolder.getContext().setAuthentication(auth);

    String accessToken = jwtUtils.createToken(auth);
    
    AuthResponse authResponse = new AuthResponse(username, "Usuario logeado con exito", accessToken, true);

    return authResponse;
        
    }
    
    private Authentication authenticate(String username, String password) {

      UserDetails userDetails = this.loadUserByUsername(username);

      if (userDetails == null) {
        throw new BadCredentialsException("El nombre de usuario o contraseña invalidas. Intente nuevamente.");
      }

      // Si el usuario esta bien hay que hacer check al password
      if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        throw new BadCredentialsException("No te dire que falla");
      }

      return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());


    }

}
