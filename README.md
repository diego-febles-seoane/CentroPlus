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
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Consola H2**: http://localhost:8080/h2-console
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

```
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

---

## 10. Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **Java 17+**: [Descargar JDK](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.8+**: [Descargar Maven](https://maven.apache.org/download.cgi)
- **Git**: [Descargar Git](https://git-scm.com/)

### Verificar instalación
```bash
java -version
mvn -version
git --version
```

---

## 11. Instalación y Configuración

### 11.1 Clonar el repositorio
```bash
git clone https://github.com/diego-febles-seoane/CentroPlus.git
cd CentroPlus-Connect
```

### 11.2 Configuración de la base de datos H2
La base de datos H2 se inicializa automáticamente con los scripts SQL ubicados en `database/.sql/`:
- `schema.sql`: Estructura de la base de datos
- `seed.sql`: Datos iniciales (opcional)

---

## 12. Estructura de Carpetas Detallada

```
CentroPlus-Connect/
├── app/                          # Módulo de aplicación JavaFX
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/es/...      # Código fuente de la app
│   │   │   └── resources/       # Recursos (propiedades, estilos, etc.)
│   │   └── test/
│   │       ├── java/            # Tests unitarios
│   │       └── resources/
│   ├── pom.xml
│   └── target/                  # Compilados y reportes
│
├── backend.api/                  # Módulo de Backend Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/es/ies/puerto/
│   │   │   │   ├── controller/  # Controladores REST
│   │   │   │   ├── service/     # Lógica de negocio
│   │   │   │   ├── repository/  # Acceso a datos (JPA)
│   │   │   │   └── entity/      # Entidades JPA
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   │       ├── java/            # Tests (servicios, repositorios, controladores)
│   │       └── resources/
│   ├── data/                    # Base de datos H2
│   ├── pom.xml
│   └── target/
│       ├── classes/             # Clases compiladas
│       ├── jacoco.exec          # Cobertura de tests
│       └── surefire-reports/    # Reportes de tests
│
├── database/                     # Configuración de base de datos
│   ├── drawio/                  # Diagramas de la arquitectura
│   ├── .sql/
│   │   ├── schema.sql           # Esquema de la BD
│   │   └── seed.sql             # Datos iniciales
│   └── README.md
│
└── README.md                     # Este archivo
```

---

## 13. Desarrollo y Debugging

### 13.1 Ejecutar con modo debug
```bash
# Backend
cd backend.api
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"

# App JavaFX
cd app
mvn javafx:run
```

### 13.2 Generar reporte de cobertura (JaCoCo)
```bash
cd backend.api
mvn test jacoco:report
# Reporte disponible en: target/site/jacoco/index.html
```

---

## 14. Documentación Adicional

- **API REST**: Consulta la documentación interactiva en [Swagger UI](http://localhost:8080/swagger-ui.html) cuando ejecutes el backend
- **Base de datos**: Ver [database/drawio/](database/README.md)
- **Diagramas**: Los diagramas de arquitectura están en `database/drawio/`

---

## 15. Notas Importantes

- La base de datos H2 se ejecuta en memoria o archivo local (no requiere servidor externo)
- Los datos se persisten en `backend.api/data/centroplus.mv.db`
- Para desarrollo, se pueden usar datos de prueba desde `database/.sql/seed.sql`
- La aplicación JavaFX es compatible con sistemas Windows, macOS y Linux con JavaFX SDK instalado
