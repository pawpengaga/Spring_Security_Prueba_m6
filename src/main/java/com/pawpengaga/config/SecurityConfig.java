package com.pawpengaga.config;

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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  // El primer paso es crear la securityFilterChain
  // Esta clase es un bean regular
  // Esto aun no tiene ningun filtro
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    /* ************************************************************************************ */
    // Ahora vamos a aplicar seguridad

    httpSecurity
      .csrf(csrf -> csrf.disable())
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(http -> {
        // Aplicaremos aqui una serie de filtros
        http.requestMatchers(HttpMethod.GET, "/", "/auth/public").permitAll();
        http.requestMatchers(HttpMethod.GET, "/auth/privado").hasAuthority("CREATE");
        http.anyRequest().authenticated();
        // http.anyRequest().denyAll();
      })
      .httpBasic(Customizer.withDefaults());

      


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
  AuthenticationProvider authenticationProvider(){

    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    authenticationProvider.setUserDetailsService(userDetailsService());
    return authenticationProvider;

  }

  // El paso 5A de la infografía
  @SuppressWarnings("deprecation")
  @Bean
  PasswordEncoder passwordEncoder(){
    return NoOpPasswordEncoder.getInstance(); // Un metodo obsoleto con fines de prueba
  }
  
  // El paso 5B de la infografía
  // Trabamos al parecer con un usuario en memoria
  @Bean
  UserDetailsService userDetailsService(){
    UserDetails upUserDetails = User.withUsername("Admin")
                                    .password("12345678")
                                    .roles("ADMIN")
                                    .authorities("READ", "CREATE")
                                    .build();
    return new InMemoryUserDetailsManager(upUserDetails);
  }

}
