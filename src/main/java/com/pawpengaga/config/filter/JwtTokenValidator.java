package com.pawpengaga.config.filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.pawpengaga.utils.JWUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {

  // Esto no es un bean ni es nada asi que no podemos usar @Autowired...
  // Toca hacer la inclusión a la antigua...

  private JWUtils jwtUtils;

  public JwtTokenValidator(JWUtils jwtUtils){
    this.jwtUtils = jwtUtils;
  }
  
  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException{

    String jwtToken = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);

    if (jwtToken != null) {
      // Si el token no es nulo entonces se le concatena la palabra bearer + un espacio
      jwtToken = jwtToken.substring(7); // El token comienza desde el 7, o sea, esta linea ignora el "Bearer "
      DecodedJWT decoded = jwtUtils.validateToken(jwtToken);

      // Si el token es valido entonces se sacan los datos
      String username = jwtUtils.extractUserName(decoded);
      String authoritiesString = jwtUtils.getSpecifiedClaim(decoded, "authorities").asString();

      Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesString);

      SecurityContext context = SecurityContextHolder.getContext();
      Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
      
      // Ahora se graba en el context
      context.setAuthentication(auth);
      SecurityContextHolder.setContext(context);

    }
    filterChain.doFilter(request, response);

  }

}
