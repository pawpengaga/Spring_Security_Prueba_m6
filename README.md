# Spring security proyecto

Vamos a usar Spring Security. Esto está dicho de tener muchos pasos así que ir con precaución.
Vamos a usar vistas pre cargadas

## Novedades

- Ahora tenemos un nuevo package llamado `com.pawpengaga.config` que se encarga de la SECURITY FILTER CHAIN de Spring Security

## Objetivos del día 2

- Usar un login propio a nivel de diseño
- Indagar en lo que hace `SECURITY CONTEXT HOLDER`

## Resultados

![alt text](pictures/image.png)

## Pendientes

- Usar las validaciones

## Flujo general de Spring Security

![alt text](pictures/image-1.png)

---

## La JWT UPDATE está aqui

Se puede ver todo tipo de documentación al respecto [aquí](https://github.com/auth0/java-jwt)

#### Cosas que tener en cuenta

- Construir el payload
- Construir la clave secreta
- Generar las utilidad de JWT, anotada como `@Component`
- Usamos el algoritmo `HMAC256`
- Generamos los datos para usar en el token
  - El Algoritmo
  - El usuario
  - Las `Authorities`
  - La información de rigor
  - La firma con el Algoritmo
- Lo validamos usando el Algoritmo
- Se genera un validor que toma el token desde el Header `Authorization`
- Si lo validado es correcto se graba en el `SecurityContextHolder`
- Existen 2 validaciones
  - La validación de autenticación: Que las credenciales de inicio de sesión sean correctas
  - La validación de sesión: Que el token sea válido
- Usamos DTO (Data To Object)
- Se trabaja en AuthController, ahora con un token de por medio
- Se utiliza por fin el filtro creado en la cadena de filtros con la forma `.addFilterBefore()`

### Observaciones

- Se pueden declarar todo tipo de variables en `application.properties`
- Usamos JWT como un filtro más dentro de la **Cadena de Filtros**
- Existen más maneras y contextos donde usar JWT, pero son más complejos