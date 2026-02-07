# Changelog - Prices Service

Todos los cambios notables en este proyecto se documentan en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/) y el proyecto sigue [Semantic Versioning](https://semver.org/).

---

## [1.1.0] - 2026-02-07

### 🎯 Mejoras en Configuración Multi-Entorno

#### ✨ Agregado

- **Spring Profiles para gestión de entornos**:
  - Perfil `dev`: Desarrollo local con H2, Swagger habilitado, logging DEBUG
  - Perfil `test`: Testing automático con H2 aislado, logging WARN, puerto aleatorio
  - Perfil `prod`: Producción con PostgreSQL, graceful shutdown, metrics/Prometheus

- **Archivos de configuración profesionales**:
  - `application.yml`: Base común con sensible defaults
  - `application-dev.yml`: Configuración para desarrollo local
  - `application-test.yml`: Configuración para testing automático
  - `application-prod.yml`: Configuración optimizada para producción

- **Seguridad y externalización**:
  - Todas las credenciales externalizadas en variables de entorno
  - Sin hardcoding de contraseñas
  - Soporte para múltiples bases de datos (H2, PostgreSQL)
  - Fallbacks seguros a configuración por defecto

- **Optimizaciones de rendimiento**:
  - Connection pooling (Hikari) optimizado por entorno
  - Dev/Test: 2-5 conexiones
  - Prod: 20-30 conexiones configurables
  - Batch processing y ordering de inserts en Hibernate

- **Documentación completa** (`CONFIGURATION.md`):
  - Guía de variables de entorno
  - Cómo ejecutar en cada perfil
  - Ejemplos de Docker y Kubernetes
  - Troubleshooting detallado
  - Configuración de logging por componente

- **Control de logging granular**:
  - Configuración por perfil (DEBUG en dev, WARN en test, ERROR en prod)
  - Logs a archivo en producción con rotación automática
  - Patterns de formato personalizados por entorno

- **Mejoría en tests**:
  - `@ActiveProfiles("test")` en `PriceControllerTest`
  - Aislamiento correcto de configuración de tests
  - Database por test completamente aislada

#### 🔧 Cambios Técnicos

- Servidor Java: 21 LTS
- Spring Boot: 3.4.0
- Spring Data JPA
- Hibernate: 6.6.2
- Hikari Connection Pool
- H2 (desarrollo/test) y PostgreSQL (producción)

#### ✅ Mejoras

- Configuración más limpia y mantenible
- Separación clara entre entornos
- Database agnostic (portable a múltiples BD)
- Production-ready (graceful shutdown, metrics, logging)
- Documentado y ejemplificado para cada escenario
- 26/26 tests pasando con configuración optimizada

---

## [1.0.0] - 2026-02-07

Se han revisado y mejorad los siguientes puntos:

**Principios SOLID**
    Se revisan principios fundamentales como Responsabilidad Única e Inversión de Dependencias.

**Arquitectura**
    Revisión de infraestrucutra en lo relativo a separación de responsabilidades. Mejora de comentarios dentro de las clases.
    
**Eficiencia**
    Mejorada la escalabilidad del producto para entornos con alta carga.
    Mejorada complejidad de algunos puntos.
    
**Testing**
    Correcciones y mejoras en clases testing.

#### Arquitectura Técnica
- **Stack**: Spring Boot 3.4.0, Spring Data JPA, Hibernate 6.6.2
- **Java**: Versión 21 LTS
- **Base de Datos**: H2 (en memoria), compatible con PostgreSQL
- **Build**: Maven 3.14.0 con Surefire para tests
- **Testing**: JUnit 5, Mockito, Spring Boot Test

---

## [0.1.0] - 2026-01-26
### Fase Inicial de Desarrollo
#### Agregado
- Estructura básica del proyecto Spring Boot
- Entidad `Price` con campos de dominio
- Controlador REST inicial
- Repositorio JPA básico
- Excepciones de dominio (`PriceNotFoundException`)

#### Corregido
- Validaciones de parámetros nulos en controlador
- Manejo de excepciones mejorado
- Formato de respuestas consistente


## Notas sobre Versiones Futuras

### v1.2.0 (Próxima)

Características planeadas:
- [ ] Caché en memoria para precios consultados frecuentemente (Redis)
- [ ] Búsqueda avanzada con filtros adicionales
- [ ] Auditoría de cambios de precios con historial
- [ ] API de administración para gestionar precios
- [ ] Validación de integridad referencial
- [ ] Rate limiting y throttling

### v2.0.0 (Futuro)

Mejoras mayores planeadas:
- [ ] Soporte para múltiples monedas
- [ ] Sistema de descuentos por volumen
- [ ] Predicción de demanda basada en ML
- [ ] GraphQL como alternativa a REST
- [ ] Event-driven architecture con Kafka
- [ ] Microservicios separados por dominio

---

**Última actualización**: 7 de febrero de 2026  
**Versión actual**: 1.1.0  
**Estado**: Productivo ✅
