# Configuration Documentation - Prices Service

Guía completa de configuración para los diferentes entornos del servicio de precios.

## 📋 Tabla de Contenidos

1. [Introducción](#introducción)
2. [Perfiles de Spring](#perfiles-de-spring)
3. [Variables de Entorno](#variables-de-entorno)
4. [Configuración por Entorno](#configuración-por-entorno)
5. [Cómo Ejecutar](#cómo-ejecutar)
6. [Propiedades Personalizadas](#propiedades-personalizadas)
7. [Troubleshooting](#troubleshooting)

---

## 📌 Introducción

El servicio Prices Service utiliza **Spring Profiles** para gestionar diferentes configuraciones según el entorno:

- **`dev`**: Desarrollo local
- **`test`**: Testing automático
- **`prod`**: Producción

### Jerarquía de Configuración

```
application.yml (base, común a todos)
    ↓
application-{profile}.yml (específico del perfil)
    ↓
Variables de Entorno ${VARIABLE}
```

---

## 🎯 Perfiles de Spring

### Activar Perfil

#### 1. **Variable de Entorno**
```bash
export SPRING_PROFILE=dev
java -jar application.jar
```

#### 2. **Property File**
En `application.properties`:
```properties
spring.profiles.active=dev
```

#### 3. **Command Line**
```bash
java -jar application.jar --spring.profiles.active=dev
```

#### 4. **Maven Spring Boot Plugin**
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

#### 5. **IDE (IntelliJ IDEA)**
- Run → Edit Configurations
- Environment variables: `SPRING_PROFILE=dev`

---

## 🌍 Variables de Entorno

### Base (Común a todos los perfiles)

```bash
# Base de Datos
DB_URL=jdbc:h2:mem:pricesdb
DB_DRIVER=org.h2.Driver
DB_USERNAME=sa
DB_PASSWORD=password
DB_POOL_SIZE=10
DB_MIN_IDLE=2
DB_CONN_TIMEOUT=30000
DB_IDLE_TIMEOUT=600000
DB_MAX_LIFETIME=1800000

# Aplicación
SERVER_PORT=8080
SERVER_CONTEXT_PATH=/
SPRING_PROFILE=dev

# JPA/Hibernate
JPA_DIALECT=org.hibernate.dialect.H2Dialect
JPA_DDL_AUTO=validate
JPA_SHOW_SQL=false
JPA_DEFER_INIT=false
HIBERNATE_FORMAT_SQL=false

# SQL Initialization
SQL_INIT_MODE=never
SQL_DATA_LOCATIONS=

# Logging
LOG_LEVEL_ROOT=INFO
LOG_LEVEL_HIBERNATE=WARN
LOG_LEVEL_SQL=WARN
LOG_LEVEL_SPRING=INFO
LOG_LEVEL_WEB=INFO
LOG_LEVEL_APP=INFO

# Swagger/OpenAPI
SWAGGER_ENABLED=false
SWAGGER_UI_ENABLED=false

# Actuator
ACTUATOR_ENDPOINTS=health
HEALTH_SHOW_DETAILS=when-authorized

# H2 Console
H2_CONSOLE_ENABLED=false
```

---

## 📊 Configuración por Entorno

### Development (`dev`)

**Archivo**: `application-dev.yml`

**Características**:
- ✅ H2 en memoria (recreado en cada startup)
- ✅ SQL visible en logs
- ✅ Swagger/OpenAPI habilitado
- ✅ Logging DEBUG
- ✅ H2 Console en `/h2-console`
- ✅ Todos los actuator endpoints
- ✅ Error details completos

**Comandos**:
```bash
# Maven
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Gradle
gradle bootRun --args='--spring.profiles.active=dev'

# JAR
java -jar app.jar --spring.profiles.active=dev
```

**Endpoints disponibles**:
- API: `http://localhost:8080/prices`
- Swagger: `http://localhost:8080/swagger-ui.html`
- H2 Console: `http://localhost:8080/h2-console`
- Actuator: `http://localhost:8080/actuator`

**Variables de Entorno típicas**:
```bash
SPRING_PROFILE=dev
LOG_LEVEL_APP=DEBUG
SWAGGER_ENABLED=true
SWAGGER_UI_ENABLED=true
H2_CONSOLE_ENABLED=true
```

---

### Testing (`test`)

**Archivo**: `application-test.yml`

**Características**:
- ✅ H2 en memoria (aislada por test)
- ✅ SQL oculto
- ✅ Swagger deshabilitado
- ✅ Logging WARN (menos ruido)
- ✅ H2 Console deshabilitada
- ✅ Puerto aleatorio (para tests paralelos)
- ✅ Datos iniciales desde `data.sql`

**Comandos**:
```bash
# Todos los tests
mvn test

# Test específico
mvn test -Dtest=PriceControllerTest

# Con cobertura
mvn clean test jacoco:report

# Tests paralelos
mvn test -DparallelForks=4
```

**Configuración automática**:
- Spring Boot Test detecta `@SpringBootTest`
- Automáticamente activa perfil `test`
- Aísla BD por test (create-drop)

**Variables útiles**:
```bash
SPRING_PROFILE=test
LOG_LEVEL_APP=INFO
DB_POOL_SIZE=2
```

---

### Production (`prod`)

**Archivo**: `application-prod.yml`

**Características**:
- ✅ PostgreSQL (recomendado)
- ✅ Connection pooling optimizado (20 máximo)
- ✅ SQL NO mostrado
- ✅ Swagger deshabilitado
- ✅ Logging WARN/ERROR solamente
- ✅ Validación de schema (nunca auto-create)
- ✅ Compresión HTTP habilitada
- ✅ Graceful shutdown
- ✅ Metrics/Prometheus habilitadas
- ✅ Logs a archivo con rotación

**Despliegue**:
```bash
# Con Docker
docker run -e SPRING_PROFILE=prod \
           -e DB_URL=jdbc:postgresql://db:5432/prices \
           -e DB_USERNAME=prices_user \
           -e DB_PASSWORD=secure_password \
           -p 8080:8080 \
           prices-service:1.0.0

# Con Kubernetes
kubectl set env deployment/prices-service \
  SPRING_PROFILE=prod \
  DB_URL=jdbc:postgresql://postgres-svc:5432/prices
```

**Variables de Entorno OBLIGATORIAS**:
```bash
SPRING_PROFILE=prod

# Base de Datos (REQUERIDAS)
DB_URL=jdbc:postgresql://hostname:5432/prices
DB_USERNAME=<usuario>
DB_PASSWORD=<password>
DB_DRIVER=org.postgresql.Driver

# Opcionales (con defaults)
DB_POOL_SIZE=20           # Connections máximas
DB_MIN_IDLE=5             # Connections mínimas
SERVER_PORT=8080
```

**Monitoreo**:
```bash
# Health Check
curl http://localhost:8080/actuator/health

# Metrics (Prometheus)
curl http://localhost:8080/actuator/metrics

# Logs
tail -f /var/log/prices-service/application.log
```

---

## 🚀 Cómo Ejecutar

### Desarrollo Local

```bash
# 1. Con variables por defecto (dev)
mvn spring-boot:run

# 2. Con perfil explícito
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# 3. Con variables personalizadas
mvn spring-boot:run \
  -Dspring-boot.run.arguments="--spring.profiles.active=dev --LOG_LEVEL_APP=DEBUG"
```

**URL accesibles**:
```
http://localhost:8080/prices?brandId=1&productId=35455&date=2020-06-14T10:00:00
http://localhost:8080/swagger-ui.html
http://localhost:8080/h2-console
```

---

### Testing

```bash
# Tests rápidos
mvn test

# Con cobertura
mvn clean test jacoco:report
open target/site/jacoco/index.html

# Test específico
mvn test -Dtest=PriceControllerTest#test1_priceAt20200614_10

# Debug de test
mvn test -Dtest=PriceControllerTest -X
```

---

### Producción

```bash
# 1. Build JAR
mvn clean package -DskipTests

# 2. Ejecutar con variables de entorno
export SPRING_PROFILE=prod
export DB_URL="jdbc:postgresql://db.example.com:5432/prices"
export DB_USERNAME="app_user"
export DB_PASSWORD="secure_password"

java -jar target/prices-service-1.0.0.jar

# 3. O con Docker
docker build -t prices-service:1.0.0 .
docker run -e SPRING_PROFILE=prod \
           -e DB_URL=jdbc:postgresql://db:5432/prices \
           -e DB_USERNAME=app_user \
           -e DB_PASSWORD=secure_password \
           -p 8080:8080 \
           prices-service:1.0.0
```

---

## 🎨 Propiedades Personalizadas

### Logging Levels

Control el nivel de logging en tiempo de ejecución:

```bash
# Development (muy verbose)
LOG_LEVEL_APP=DEBUG
LOG_LEVEL_SPRING=DEBUG
LOG_LEVEL_HIBERNATE=DEBUG

# Production (minimal)
LOG_LEVEL_APP=INFO
LOG_LEVEL_SPRING=WARN
LOG_LEVEL_HIBERNATE=WARN
```

**Niveles disponibles** (de mayor a menor verbosidad):
```
TRACE > DEBUG > INFO > WARN > ERROR > FATAL > OFF
```

### Connection Pool (Hikari)

Para optimizar según carga:

```bash
# Alto concurrency
DB_POOL_SIZE=30
DB_MIN_IDLE=10

# Bajo concurrency
DB_POOL_SIZE=5
DB_MIN_IDLE=1

# Timeouts
DB_CONN_TIMEOUT=10000    # ms, timeout para obtener conexión
DB_IDLE_TIMEOUT=600000   # ms, 10 min idle antes de cerrar
DB_MAX_LIFETIME=1800000  # ms, 30 min máximo de vida
```

### JPA/Hibernate

```bash
# Auto-create schema (SOLO dev/test)
JPA_DDL_AUTO=create-drop  # create | create-drop | update | validate

# Performance
HIBERNATE_FORMAT_SQL=true  # Beautify SQL output
JPA_SHOW_SQL=true          # Log all SQL statements

# Batch optimization (prod)
HIBERNATE_BATCH_SIZE=25
HIBERNATE_FETCH_SIZE=50
```

---

## 🔍 Troubleshooting

### Problema: "Profile not found"

```
Error: No such file or directory: 'application-custom.yml'
```

**Solución**:
- Asegúrate que el archivo `application-{profile}.yml` existe
- Usa solo `dev`, `test`, o `prod`
- Verifica la ruta: `src/main/resources/`

---

### Problema: "Connection Refused"

```
Error: java.sql.SQLException: Cannot connect to database
```

**Solución**:
```bash
# Verifica BD está corriendo
docker ps | grep postgres

# Verifica URL de conexión
echo $DB_URL  # jdbc:postgresql://localhost:5432/prices

# Test conexión
psql -h localhost -U prices_user -d prices
```

---

### Problema: "Hibernat Dialect not found"

```
Error: Unknown database [org.postgresql.Driver]
```

**Solución**:
```bash
# Verifica el dialect es correcto
JPA_DIALECT=org.hibernate.dialect.PostgreSQLDialect

# Depende del DB:
# H2:        org.hibernate.dialect.H2Dialect
# PostgreSQL: org.hibernate.dialect.PostgreSQLDialect
# MySQL:     org.hibernate.dialect.MySQL8Dialect
# Oracle:    org.hibernate.dialect.OracleDialect
```

---

### Problema: "Actuator Endpoints Not Available"

```
http://localhost:8080/actuator/health → 404
```

**Solución**:
```bash
# Verifica están expuestos
ACTUATOR_ENDPOINTS=health,metrics,prometheus,env

# O habilita todos en dev
ACTUATOR_ENDPOINTS=*
```

---

### Problema: "Swagger UI No Aparece"

```
http://localhost:8080/swagger-ui.html → 404
```

**Solución**:
```bash
# Verifica está habilitado
SWAGGER_ENABLED=true
SWAGGER_UI_ENABLED=true

# Verifica el perfil es dev
SPRING_PROFILE=dev

# Accede a OpenAPI JSON
http://localhost:8080/v3/api-docs
```

---

## 📚 Referencia Rápida

| Tarea | Comando |
|-------|---------|
| **Dev Local** | `mvn spring-boot:run` |
| **Dev con Logs** | `mvn spring-boot:run -Dspring-boot.run.arguments="--LOG_LEVEL_APP=DEBUG"` |
| **Tests** | `mvn test` |
| **Build JAR** | `mvn clean package` |
| **Run JAR** | `java -jar app.jar --spring.profiles.active=prod` |
| **Docker Build** | `docker build -t prices-service:1.0.0 .` |
| **Docker Run** | `docker run -e SPRING_PROFILE=prod -p 8080:8080 prices-service:1.0.0` |

---

## 🔗 Enlaces Útiles

- [Spring Boot Profiles](https://spring.io/blog/2011/02/14/spring-framework-3-1-m1-introduced-support-for-profiles/)
- [Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config)
- [Spring Data JPA Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties.data.spring.jpa)
- [Logging Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.logging)

---

**Última actualización**: 7 de febrero de 2026  
**Versión del documento**: 1.0.0
