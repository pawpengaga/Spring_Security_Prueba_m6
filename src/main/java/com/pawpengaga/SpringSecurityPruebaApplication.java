package com.pawpengaga;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.pawpengaga.model.Permiso;
import com.pawpengaga.model.Role;
import com.pawpengaga.model.RoleEnum;
import com.pawpengaga.model.Usuario;
import com.pawpengaga.repository.UsuarioRepository;

@SpringBootApplication
public class SpringSecurityPruebaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityPruebaApplication.class, args);
	}


	// @Bean
	// CommandLineRunner init(UsuarioRepository userRepo) {
	// 	// Vamos a poblar los datos
	// 	return args -> {
	// 		String claveEncriptada = new BCryptPasswordEncoder().encode("12345678");
	// 		String claveEncriptadados = new BCryptPasswordEncoder().encode("12345678");
	// 		String claveEncriptadatres = new BCryptPasswordEncoder().encode("12345678");

	// 		// Generamos permisos
	// 		// @Builder de Lombok permite hacer objetos con sintaxis de punto? ._.
	// 		Permiso leer = Permiso.builder().name("READ").build();
	// 		Permiso crear = Permiso.builder().name("CREATE").build();
	// 		Permiso actualizar = Permiso.builder().name("UPDATE").build();
	// 		Permiso eliminar = Permiso.builder().name("DELETE").build();

	// 		// GENERAMOS LOS ROLES
	// 		Role admin = Role.builder()
	// 								 .role(RoleEnum.ADMIN)
	// 								 .permisos(Set.of(leer, crear, actualizar))
	// 								 .build();

	// 		Role usuario = Role.builder()
	// 								 .role(RoleEnum.USER)
	// 								 .permisos(Set.of(leer, crear))
	// 								 .build();

	// 		Role invitado = Role.builder()
	// 								 .role(RoleEnum.INVITED)
	// 								 .permisos(Set.of(leer))
	// 								 .build();

	// 		Role dev = Role.builder()
	// 								 .role(RoleEnum.DEVELOPER)
	// 								 .permisos(Set.of(leer, crear, actualizar, eliminar))
	// 								 .build();


	// 		/* VAMOS A CREAR LOS USUARIOS */
	// 		/* (Esto es lo que va a la base de datos) */

	// 		Usuario admi = Usuario.builder()
	// 			.name("Erick Rivera")
	// 			.email("admin@mail.com")
	// 			.password(claveEncriptada) // Fue definida arriba
	// 			.roles(Set.of(admin, dev))
	// 			.isEnabled(true)
	// 			.accountNoExpired(true)
	// 			.accountNoLocked(true)
	// 			.credentialNoExpired(true)
	// 			.build();

	// 		Usuario ana = Usuario.builder()
	// 			.name("Ana Banana")
	// 			.email("anabanana@mail.com")
	// 			.password(claveEncriptadados) // Fue definida arriba
	// 			.roles(Set.of(usuario))
	// 			.isEnabled(true)
	// 			.accountNoExpired(true)
	// 			.accountNoLocked(true)
	// 			.credentialNoExpired(true)
	// 			.build();

	// 		Usuario mari = Usuario.builder()
	// 			.name("Maria Sandia")
	// 			.email("mariasandia@mail.com")
	// 			.password(claveEncriptadatres) // Fue definida arriba
	// 			.roles(Set.of(invitado))
	// 			.isEnabled(true)
	// 			.accountNoExpired(true)
	// 			.accountNoLocked(true)
	// 			.credentialNoExpired(true)
	// 			.build();

			
	// 			/* FINALMENTE GRABAMOS */
	// 			userRepo.saveAll(List.of(admi, ana, mari));
	// 			// Podemos usar el metodo saveAll de Hibernate
			

	// 	};
	// }

}
