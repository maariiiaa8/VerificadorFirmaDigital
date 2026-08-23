# LetterdBox – CRUD Spring Boot

Aplicación de consola en Java para gestionar tu propia biblioteca de películas vistas y pendientes, inspirada en Letterboxd. CRUD completo sobre una base de datos MongoDB.

## Funcionalidades

- Añadir películas (título, director, año, género, duración, puntuación, reseña, vista/no vista)
- Listar todas las películas guardadas
- Buscar por título, por género, o filtrar por vistas/no vistas
- Actualizar cualquier campo de una película existente
- Eliminar películas, con confirmación previa
- Validaciones de datos (año, puntuación 0-5, campos obligatorios)

## Stack tecnológico

- **Java 21**
- **Spring Boot 3.2** (arranque de contexto, inyección de dependencias)
- **Spring Data MongoDB** como capa de persistencia
- **Maven** como gestor de dependencias
- JUnit 5 para tests

> Nota: el `pom.xml` incluye dependencias de JavaFX pensadas para una futura interfaz gráfica; por ahora la aplicación funciona por consola.

## Arquitectura

```
Main (menú por consola)
  └── PeliculaService (lógica de negocio)
        └── PeliculaRepository (Spring Data MongoDB)
              └── Pelicula (documento MongoDB)
```

## Puesta en marcha

Requisitos: Java 21, Maven, MongoDB corriendo en `localhost:27017`.

```bash
# Compilar
./mvnw clean install

# Ejecutar
./mvnw spring-boot:run
```

La aplicación conecta por defecto a la base de datos `letterboxdDB`. Puedes cambiar la conexión en `src/main/resources/application.properties`.

## Tests

```bash
./mvnw test
```
