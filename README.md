# Backend NutriVida

Microservicios en Spring Boot 4.1.2 y Java 21, registrados en Eureka y expuestos al front a través de un API Gateway.

## Servicios y puertos

| Servicio | Puerto | Rutas | Base de datos |
|---|---|---|---|
| eureka | 8761 | Panel en http://localhost:8761 | — |
| api-gateway | 8080 | Única entrada del front | — |
| ms-usuarios | 8081 | `/api/auth/**`, `/api/usuarios/**` | bd_usuarios |
| ms-profesionales | 8082 | `/api/profesionales/**` | bd_profesionales |
| ms-agendar | 8083 | `/api/citas/**`, `/api/prestaciones` | bd_agenda |
| ms-pagos | 8084 | `/api/pagos`, `/api/boletas/**` | bd_pagos |
| ms-fichas | 8085 | `/api/fichas/**` | bd_fichas |
| ms-contacto | 8086 | `/api/mensajes/**` | bd_contacto |

## Orden para levantar

1. MySQL (Laragon), usuario `root` sin contraseña. Las bases se crean solas.
2. `eureka`
3. Los 6 microservicios, en cualquier orden.
4. `api-gateway`
5. El front (`npm run dev`, puerto 5173).

Cada proyecto se levanta desde su carpeta con `./mvnw spring-boot:run`.
Al encender, los micros tardan unos segundos en aparecer en Eureka; si el gateway responde 503 al principio, basta esperar un momento.

## Seguridad

- ms-usuarios crea el token JWT al iniciar sesión; los demás micros solo lo validan con la misma `jwt.secret`.
- Roles: `Cliente`, `medico` y `admin`.
- El CORS para el front se configura en el gateway.
- Cada micro tiene su Swagger en `http://localhost:<puerto>/swagger-ui.html`.
