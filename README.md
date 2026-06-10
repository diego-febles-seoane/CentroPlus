# CentroPlus Connect — Proyecto Intermodular

## 1. Introducción
### Objetivo general
El objetivo de este proyecto es desarrollar una solución tecnológica completa e intermodular llamada **CentroPlus Connect**, orientada a la gestión de un centro académico y deportivo.

---

## 2. Estructura del Proyecto
```text
CentroPlus/
├── app/                          # Aplicación JavaFX (existente)
│
├── backend.api/                   # Backend API REST con Spring Boot
│
├── database/                      # Base de datos
│   ├── drawio/
│   ├── .sql/
│   │   ├── schema.sql
│   │   └── seed.sql
│   └── README.md
│
└── README.md
```

---

## 3. Tecnologías Principales
| Componente | Tecnología |
|---|---|
| Aplicación de escritorio | JavaFX |
| Backend API | Spring Boot 3.2.5 |
| Base de datos | H2 Database |
| Gestión de dependencias | Maven |
| Documentación API | Swagger/OpenAPI |
| Java | 17+ |

---

## 4. Análisis Simple
El proyecto está compuesto por:
- **Aplicación JavaFX**: Aplicación de escritorio para la gestión del centro.
- **Backend API**: API REST con Spring Boot para la gestión de datos.
- **Base de datos**: H2 para la persistencia de datos.

---

## 5. Cómo Ejecutar el Proyecto

### 5.1 Backend API REST
Para ejecutar el backend:
```bash
# Entra a la carpeta del backend
cd backend.api

# Ejecutar con Maven
mvn spring-boot:run
```

**Accesos:
- Swagger UI: http://localhost:8080/swagger-ui.html
  - JDBC URL: `jdbc:h2:file:./data/centroplus`
  - Usuario: `sa`
  - Contraseña: (dejar vacío)

### 5.2 Aplicación JavaFX
Para ejecutar la aplicación de escritorio, tienes dos opciones:

#### Usar el plugin de JavaFX (recomendado)
```bash
# Entra a la carpeta de la app
cd app

# Ejecutar con Maven
mvn javafx:run
```

## 6. Endpoints de la API REST

### Usuarios
| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/usuarios | Obtener todos los usuarios |
| GET | /api/usuarios/{id} | Obtener usuario por ID |
| POST | /api/usuarios | Crear nuevo usuario |
| PUT | /api/usuarios/{id} | Actualizar usuario |
| DELETE | /api/usuarios/{id} | Eliminar usuario |

### Actividades
| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/actividades | Obtener todas las actividades |
| GET | /api/actividades/{id} | Obtener actividad por ID |
| POST | /api/actividades | Crear nueva actividad |
| PUT | /api/actividades/{id} | Actualizar actividad |
| DELETE | /api/actividades/{id} | Eliminar actividad |

### Reservas
| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/reservas | Obtener todas las reservas |
| GET | /api/reservas/{id} | Obtener reserva por ID |
| POST | /api/reservas | Crear nueva reserva |
| PUT | /api/reservas/{id} | Actualizar reserva |
| DELETE | /api/reservas/{id} | Eliminar reserva |

### Incidencias
| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/incidencias | Obtener todas las incidencias |
| GET | /api/incidencias/{id} | Obtener incidencia por ID |
| POST | /api/incidencias | Crear nueva incidencia |
| PUT | /api/incidencias/{id} | Actualizar incidencia |
| DELETE | /api/incidencias/{id} | Eliminar incidencia |

---

## 7. Funcionalidades Principales
- Gestión de usuarios
- Gestión de actividades
- Gestión de reservas
- Gestión de incidencias

---

## 8. Tests del Proyecto
El backend incluye tests para cumplir con los requisitos:

### Tipos de tests
1. **Tests de servicios**: 20 tests (5 por cada servicio)
2. **Tests de repositorios**: 10 tests (5 por cada repositorio)
3. **Tests de API REST**: 5 tests

### Ejecutar los tests
```bash
cd backend.api

# Ejecutar todos los tests
mvn test

# Generar reporte de tests
mvn surefire-report:report
```

### Framework de testing
- **JUnit 5**: Framework de testing
- **Mockito**: Para mocking
- **@DataJpaTest**: Para tests de repositorios
- **@WebMvcTest**: Para tests de controladores
- **MockMvc**: Para probar endpoints REST

---

## 9. Arquitectura
```
APP JavaFX
      ↓
 API REST (Spring Boot)
      ↓
Servicios
      ↓
Repositorios (JPA)
      ↓
 Base de datos (H2)
```
