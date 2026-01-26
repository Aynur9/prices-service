# Prices Service - Ejercicio Técnico

Servicio REST para consultar precios aplicables a productos según cadena y fecha.

## 📋 Descripción

Aplicación Spring Boot que provee un endpoint REST para consultar el precio aplicable a un producto de una cadena en una fecha determinada. Cuando existen múltiples tarifas aplicables, se selecciona la de mayor prioridad.

## 🏗️ Arquitectura

El proyecto sigue **Arquitectura Hexagonal (Puertos y Adaptadores)** con separación clara de responsabilidades:

```
src/main/java/com/zara/prices/
├── domain/                          # Capa de Dominio (núcleo)
│   ├── model/
│   │   └── Price.java              # Entidad de dominio
│   ├── port/
│   │   ├── in/
│   │   │   └── GetApplicablePriceUseCase.java   # Puerto de entrada
│   │   └── out/
│   │       └── PriceRepository.java             # Puerto de salida
│   └── service/
│       ├── PriceDomainService.java              # Lógica de negocio
│       └── PriceNotFoundException.java          # Excepción de dominio
├── application/                     # Capa de Aplicación
│   └── GetApplicablePriceService.java           # Caso de uso
└── infrastructure/                  # Capa de Infraestructura (adaptadores)
    ├── web/                        # Adaptador REST
    │   ├── PriceController.java
    │   ├── PriceResponse.java
    │   └── PriceWebMapper.java
    ├── persistence/                # Adaptador JPA
    │   ├── PriceEntity.java
    │   ├── PriceJpaRepository.java
    │   ├── PriceRepositoryImpl.java
    │   ├── PriceJpaMapper.java
    │   └── SpringDataPriceRepository.java
    └── configuration/
        ├── PriceConfiguration.java
        └── OpenApiConfiguration.java
```

### Principios SOLID aplicados:

- **S**ingle Responsibility: Cada clase tiene una única responsabilidad
- **O**pen/Closed: Abierto a extensión mediante puertos/adaptadores
- **L**iskov Substitution: Las implementaciones respetan los contratos de las interfaces
- **I**nterface Segregation: Interfaces específicas y cohesivas
- **D**ependency Inversion: Dependencias hacia abstracciones (puertos)

## 🚀 Tecnologías

- **Java 25**
- **Spring Boot 3.4.0**
- **Spring Data JPA**
- **H2 Database** (en memoria)
- **SpringDoc OpenAPI 2.3.0** (Swagger)
- **Lombok**
- **Maven 3.9.12**
- **JUnit 5 + Mockito**

## 📦 Instalación y Ejecución

### Prerequisitos
- JDK 25 o superior
- Maven 3.9.12 (incluido en el proyecto con Maven Wrapper)

### Compilar el proyecto
```bash
./mvnw clean install
```

### Ejecutar la aplicación
```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

### Ejecutar tests
```bash
./mvnw test
```

## 📚 Documentación de la API

### Swagger UI (Interfaz Interactiva)

Accede a la documentación interactiva de la API en:

**🔗 http://localhost:8080/swagger-ui.html**

Desde Swagger UI puedes:
- Ver todos los endpoints disponibles
- Consultar esquemas de request/response
- Probar los endpoints directamente
- Ver ejemplos de uso
- Descargar la especificación OpenAPI

### OpenAPI Specification

- **JSON**: http://localhost:8080/v3/api-docs
- **YAML**: http://localhost:8080/v3/api-docs.yaml

## 🔌 API REST

### Endpoint: Consultar Precio Aplicable

**GET** `/prices`

Obtiene el precio aplicable para un producto de una cadena en una fecha determinada.

#### Parámetros de entrada (Query Parameters):

| Parámetro | Tipo | Formato | Descripción | Ejemplo |
|-----------|------|---------|-------------|---------|
| `date` | LocalDateTime | ISO-8601 | Fecha de aplicación | `2020-06-14T10:00:00` |
| `productId` | Long | Numérico | ID del producto | `35455` |
| `brandId` | Long | Numérico | ID de la cadena (1=ZARA) | `1` |

#### Respuesta exitosa (200 OK):

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```

#### Respuesta de error (404 Not Found):

```json
{
  "timestamp": "2026-01-26T20:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Price not found",
  "path": "/prices"
}
```

### Ejemplos de uso:

#### Con cURL:

```bash
# Test 1: 10:00 del día 14 - Precio: 35.50 EUR (tarifa 1)
curl "http://localhost:8080/prices?date=2020-06-14T10:00:00&productId=35455&brandId=1"

# Test 2: 16:00 del día 14 - Precio: 25.45 EUR (tarifa 2, mayor prioridad)
curl "http://localhost:8080/prices?date=2020-06-14T16:00:00&productId=35455&brandId=1"

# Test 3: 21:00 del día 14 - Precio: 35.50 EUR (tarifa 1)
curl "http://localhost:8080/prices?date=2020-06-14T21:00:00&productId=35455&brandId=1"

# Test 4: 10:00 del día 15 - Precio: 30.50 EUR (tarifa 3, mayor prioridad)
curl "http://localhost:8080/prices?date=2020-06-15T10:00:00&productId=35455&brandId=1"

# Test 5: 21:00 del día 16 - Precio: 38.95 EUR (tarifa 4)
curl "http://localhost:8080/prices?date=2020-06-16T21:00:00&productId=35455&brandId=1"
```

#### Con PowerShell:

```powershell
Invoke-RestMethod "http://localhost:8080/prices?date=2020-06-14T10:00:00&productId=35455&brandId=1"
```

#### Desde Swagger UI:

1. Abre http://localhost:8080/swagger-ui.html
2. Expande el endpoint GET /prices
3. Click en "Try it out"
4. Rellena los parámetros
5. Click en "Execute"

## 💾 Base de Datos

### H2 Console

La consola H2 está disponible en: `http://localhost:8080/h2`

**Credenciales:**
- JDBC URL: `jdbc:h2:mem:pricesdb`
- Usuario: `sa`
- Password: `password`

### Datos de ejemplo

La aplicación se inicializa con los siguientes datos de prueba:

| BRAND_ID | START_DATE | END_DATE | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE | CURRENCY |
|----------|------------|----------|------------|------------|----------|-------|----------|
| 1 | 2020-06-14 00:00:00 | 2020-12-31 23:59:59 | 1 | 35455 | 0 | 35.50 | EUR |
| 1 | 2020-06-14 15:00:00 | 2020-06-14 18:30:00 | 2 | 35455 | 1 | 25.45 | EUR |
| 1 | 2020-06-15 00:00:00 | 2020-06-15 11:00:00 | 3 | 35455 | 1 | 30.50 | EUR |
| 1 | 2020-06-15 16:00:00 | 2020-12-31 23:59:59 | 4 | 35455 | 1 | 38.95 | EUR |

### Lógica de prioridad

Cuando múltiples tarifas son aplicables en una fecha:
- Se selecciona la tarifa con **mayor PRIORITY**
- El campo PRIORITY actúa como desambiguador

## 🧪 Testing

### Cobertura de tests

El proyecto incluye tests en todos los niveles:

#### Tests Unitarios
- **PriceTest**: Tests del modelo de dominio
- **PriceDomainServiceTest**: Tests de la lógica de negocio
- **GetApplicablePriceServiceTest**: Tests del caso de uso
- **PriceRepositoryAdapterTest**: Tests del adaptador de persistencia

#### Tests de Integración
- **PriceControllerTest**: 5 tests de integración que validan el endpoint REST con los casos solicitados

### Ejecutar tests específicos

```bash
# Todos los tests
./mvnw test

# Solo tests de integración
./mvnw test -Dtest=PriceControllerTest

# Un test específico
./mvnw test -Dtest=PriceControllerTest#test1_priceAt20200614_10
```

### Resultados esperados

Todos los tests deben pasar (11/11):
- 6 tests unitarios ✅
- 5 tests de integración ✅

## 📁 Estructura del Proyecto

```
prices-service/
├── .mvn/                           # Configuración Maven Wrapper
│   ├── maven.config               # Configuración Maven personalizada
│   └── wrapper/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/zara/
│   │   │       ├── DemoApplication.java
│   │   │       └── prices/         # Ver sección Arquitectura
│   │   └── resources/
│   │       ├── application.yml     # Configuración Spring Boot
│   │       └── data.sql           # Datos de inicialización
│   └── test/
│       └── java/                   # Tests (ver sección Testing)
├── pom.xml
├── mvnw / mvnw.cmd                # Maven Wrapper
└── README.md
```

## ⚙️ Configuración

### application.yml

Configuración principal de la aplicación:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:pricesdb
    driver-class-name: org.h2.Driver
    username: sa
    password: password
  h2:
    console:
      enabled: true
  jpa:
    hibernate:
      ddl-auto: create
    defer-datasource-initialization: true
  sql:
    init:
      mode: always

# Swagger/OpenAPI
springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html
    enabled: true
```

## 🎯 Decisiones de Diseño

### 1. Arquitectura Hexagonal
- **Desacoplamiento**: El dominio no conoce los adaptadores
- **Testabilidad**: Fácil mockeo de dependencias
- **Mantenibilidad**: Cambios en infraestructura no afectan al negocio

### 2. Eficiencia de Consultas
- Query optimizada con índices en campos de búsqueda
- Ordenamiento en base de datos (`ORDER BY priority DESC`)
- Retorno del primer resultado (mayor prioridad)

### 3. Documentación con OpenAPI
- Documentación automática mediante anotaciones
- Swagger UI para pruebas interactivas
- Especificación OpenAPI 3.0 estándar

### 4. Manejo de Errores
- Excepción de dominio `PriceNotFoundException`
- Traducción a HTTP 404 en el controlador

### 5. Buenas Prácticas REST
- Verbos HTTP semánticos (GET)
- Códigos de estado HTTP apropiados (200, 404, 400)
- Validación de parámetros con anotaciones Spring
- Formato de fecha estándar ISO-8601

## 📊 Cumplimiento de Requisitos

| Requisito | Estado | Notas |
|-----------|--------|-------|
| ✅ Arquitectura Hexagonal | Completo | Separación clara de capas |
| ✅ Endpoint REST GET | Completo | Con validaciones y buenas prácticas |
| ✅ Parámetros de entrada | Completo | date, productId, brandId |
| ✅ Datos de salida | Completo | Todos los campos requeridos |
| ✅ Base de datos H2 | Completo | En memoria, inicializada con datos |
| ✅ Tests de integración | Completo | 5 tests solicitados |
| ✅ SOLID | Completo | Aplicado en toda la arquitectura |
| ✅ Calidad de código | Completo | Código limpio y bien estructurado |
| ✅ Control de versiones | Completo | Git con .gitignore configurado |
| ✅ Configuración | Completo | application.yml centralizado |
| ✅ Eficiencia | Completo | Queries optimizadas, única consulta |
| ✅ README | Completo | Documentación completa |
| ✅ Documentación API | Completo | Swagger/OpenAPI implementado |
| ✅ Comentarios JavaDoc | Completo | Todas las clases documentadas |

## 🔗 Enlaces Útiles

- **API REST**: http://localhost:8080/prices
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **H2 Console**: http://localhost:8080/h2

## 👨‍💻 Autor

Proyecto desarrollado como ejercicio técnico para demostrar:
- Diseño de arquitectura limpia
- Buenas prácticas de desarrollo
- Testing exhaustivo
- Documentación profesional

## 📄 Licencia

Este es un proyecto de ejercicio técnico.
