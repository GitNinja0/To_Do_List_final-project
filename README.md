# To-Do List Backend (Spring Boot)

Este es el repositorio backend de la aplicación To-Do List. Provee una API REST construida con Spring Boot que gestiona usuarios, roles, tareas, categorías y etiquetas. 

## Arquitectura y Tecnologías

- **Framework**: Spring Boot 3 (Java 21)
- **Base de Datos**: MySQL (y PostgreSQL soportado) con Spring Data JPA
- **Seguridad**: Spring Security con JSON Web Tokens (JWT) y codificación Bcrypt para contraseñas.
- **Construcción**: Maven
- **Lombok**: Para reducir el código boilerplate.

## Estructura de Paquetes

- `auth`: Autenticación y registro de usuarios.
- `users`: Gestión de usuarios, edición de perfil y control de acceso. Implementa paginación del lado del servidor para alta escalabilidad.
- `roles`: Gestión de roles (USER, GESTOR, ADMIN).
- `tasks`: Controladores y servicios para el CRUD de tareas, asociación de etiquetas y filtrado.
- `categories`: Manejo de categorías tanto para acceso público como gestión de administrador.
- `tags`: Gestión de etiquetas asignables a las tareas.
- `config`: Configuraciones globales como CORS y Spring Security.

## Ejecución Local

Para correr la aplicación de forma local usando el perfil por defecto (o H2 en memoria / BBDD local):

```bash
./mvnw clean spring-boot:run
```

## Ejecución con Docker Compose

El proyecto incluye un archivo `docker-compose.yml` que levanta tanto la base de datos MySQL como la propia aplicación Spring Boot.

```bash
docker-compose up --build
```

Asegúrate de que el puerto `8080` (App) y `3306` (MySQL) estén libres en tu máquina.

## Endpoints Principales

- `POST /api/auth/register` - Registro de nuevos usuarios
- `GET /api/auth/login` - Inicio de sesión
- `GET /api/users/` - Retorna los usuarios paginados (con soporte de búsqueda y filtros)
- `GET /api/task` - Obtener tareas del usuario logueado
- `POST /api/task` - Crear nueva tarea
