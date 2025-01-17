package com.pawpengaga.config;

import javax.swing.text.html.HTML;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.pawpengaga.config.filter.JwtTokenValidator;
import com.pawpengaga.service.UserDetailsServiceImpl;
import com.pawpengaga.utils.JWUtils;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  JWUtils jwtUtils;

  // El primer paso es crear la securityFilterChain
  // Esta clase es un bean regular
  // Esto aun no tiene ningun filtro
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    /* ************************************************************************************ */
    // Ahora vamos a aplicar seguridad

    httpSecurity
      .csrf(csrf -> csrf.disable())
      // .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Esta linea significa que nunca se creará una session y que siempre se hará redirección al index
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
      .authorizeHttpRequests(http -> {

        /* Aplicaremos aqui una serie de filtros */

        http.requestMatchers(HttpMethod.GET, "/","/pages/public","/401","/auth/login", "/auth/log-in").permitAll();
        http.requestMatchers(HttpMethod.POST, "/auth/log-in").permitAll();
        http.requestMatchers(HttpMethod.GET, "/pages/privado").hasAnyRole("ADMIN", "DEVELOPER", "USER");
        http.requestMatchers(HttpMethod.GET, "/pages/privado").hasAnyAuthority("ROLE_USER");
        http.requestMatchers(HttpMethod.GET, "/pages/config").hasRole("DEVELOPER");
        http.requestMatchers(HttpMethod.GET, "/pages/config").hasAnyAuthority("ROLE_USER");
        
        // http.requestMatchers(HttpMethod.GET, "/auth/privado").hasAnyAuthority("UPDATE"); // X
        // http.requestMatchers(HttpMethod.GET, "auth/config").hasAnyAuthority("DELETE"); // X
        

        http.anyRequest().authenticated();

        // http.anyRequest().denyAll();
        
      })
      .formLogin(formlogin -> formlogin
        .usernameParameter("correo")
        .passwordParameter("clave")
        .loginPage("/auth/login")
        .loginProcessingUrl("/auth/login")
      ) // El mismo SpringSecurity crear una ventana de login

      .exceptionHandling((exceptionHandling) -> exceptionHandling.accessDeniedPage("/401"))
      // Poner aqui un 404

      .httpBasic(Customizer.withDefaults()) // httpBasic es una de las tantas formas de autenticarse, ver Postman

      .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class);

    /* ************************************************************************************ */

    return httpSecurity.build();

  }

  // Este metodo corresponde al paso 3 de la infografía
  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  // Nuestro proveedor, aún vacío, el paso 4 de la infografía
  @Bean
  AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailService){

    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    authenticationProvider.setUserDetailsService(userDetailService);
    return authenticationProvider;

  }

  // El paso 5A de la infografía
  // @SuppressWarnings("deprecation")
  @Bean
  PasswordEncoder passwordEncoder(){
    
    // return NoOpPasswordEncoder.getInstance(); // Un metodo obsoleto con fines de prueba
    return new BCryptPasswordEncoder();
  }
  
  // El paso 5B de la infografía
  // Trabamos al parecer con un usuario en memoria
  // @Bean
  // UserDetailsService userDetailsService(){
  //   UserDetails upUserDetails = User.withUsername("Admin")
  //                                   .password("12345678")
  //                                   .roles("ADMIN")
  //                                   .authorities("READ", "CREATE")
  //                                   .build();
  //   return new InMemoryUserDetailsManager(upUserDetails);
  // }

}
