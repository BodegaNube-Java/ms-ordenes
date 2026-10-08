##  Variables de Entorno

```env
DB_URL=jdbc:postgresql://localhost:5432/ordenes_db
DB_USER=postgres
DB_PASSWORD=postgres

Microservicio de órdenes 

## Descripción

`ms-ordenes` es el servicio encargado de la orquestación, creación y seguimiento del ciclo de vida de los pedidos. Coordina de forma síncrona la reserva de stock consumiendo `ms-inventario` (`POST /inventario/reservar`) y gestiona las reglas de negocio y estados de las órdenes según la disponibilidad del catálogo.

---

##  Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.x**
- **Spring Data JPA** (Persistencia y Mapeo ORM)
- **Spring WebFlux / WebClient** (Cliente HTTP para integración síncrona)
- **PostgreSQL** (Base de datos relacional)
- **Maven** (Gestión de dependencias y construcción del artefacto)

---

##  Arquitectura en Capas (CSR)

El proyecto implementa la arquitectura **Controller - Service - Repository ,model(CSR)**:

Nuestra arquitectura se basa en el patrón en capas CSR junto con componentes transversales de soporte:

Controller (@RestController): Maneja la capa de entrada REST (/ordenes), valida las peticiones y retorna las respuestas HTTP correspondientes.

Service (@Service): Contiene la lógica de negocio y orquesta la comunicación síncrona enviando las peticiones a ms-inventario.

Repository (@Repository): Gestiona la persistencia de datos en PostgreSQL utilizando Spring Data JPA.

Model (@Entity): Define las entidades de dominio y mapea las tablas en la base de datos con sus anotaciones JPA.

Config & Exception (Controlador Global): Implementamos un @RestControllerAdvice para capturar de forma centralizada las excepciones de negocio (como el error 409 Conflict cuando no hay stock) y transformar la respuesta limpiamente sin romper el flujo del sistema."

dto :Para definir los objetos de transferencia de datos (Data Transfer Objects)

##  Despliegue con Docker

El proyecto incluye un archivo `docker-compose.yml` para levantar rápidamente la instancia de PostgreSQL local
