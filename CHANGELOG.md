# Changelog - Prices Service

Todos los cambios notables en este proyecto se documentan en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/) y el proyecto sigue [Semantic Versioning](https://semver.org/).

---

## [1.0.0] - 2026-02-07

### 🎉 Cambios Iniciales - Primera Release Productiva

#### ✨ Agregado

- **Arquitectura Hexagonal**: Implementación completa del patrón Ports & Adapters
  - Separación clara entre capas (application, domain, infrastructure)
  - Interfaces de puertos bien definidas para la persistencia
  
- **Servicio de Precios Completo**: API REST para consultar precios aplicables
  - Endpoint: `GET /prices?brandId={id}&productId={id}&date={date}`
  - Soporta consultas por marca, producto y fecha
  - Respuesta con información completa del precio (lista, prioridad, rango vigente)

- **Optimizaciones para Alta Carga**:
  - Método de proyección `findApplicableProjected()` para escenarios de alta concurrencia
  - Paginación mediante Spring Data JPA
  - DTOs para reducir footprint de memoria
  - Índices de base de datos en columnas de búsqueda (brandId, productId)

- **Validaciones Robustas**:
  - Anotaciones Jakarta Validation: `@NotNull`, `@Positive`
  - Handler global de excepciones con respuestas HTTP consistentes
  - Mensajes de error descriptivos para cada caso

- **Documentación OpenAPI/Swagger**:
  - Documentación automática de API con `springdoc-openapi`
  - Ejemplos de request/response para cada endpoint
  - Modelos de respuesta claramente definidos

- **Cobertura de Tests Completa**:
  - 26 tests unitarios e integración (100% pasando)
  - Coverage en servicios, controladores, repositorios y dominio
  - Tests con JUnit 5 y Mockito
  - Datos de prueba con script SQL

- **Configuración Profesional**:
  - H2 como base de datos en memoria para desarrollo y testing
  - Propiedades configurables via `application.yml`
  - Logging estructurado con niveles apropiados
  - Gestión de transacciones automática

- **Estándares de Código**:
  - SOLID principles implementados
  - Convenciones de nombres consistentes
  - Documentación Javadoc en métodos públicos
  - Formato de código con indentación consistente

#### 🔧 Técnico

- **Stack**: Spring Boot 3.4.0, Spring Data JPA, Hibernate 6.6.2
- **Java**: Versión 21 LTS
- **Base de Datos**: H2 (en memoria), compatible con PostgreSQL
- **Build**: Maven 3.14.0 con Surefire para tests
- **Testing**: JUnit 5, Mockito, Spring Boot Test

---

## [0.1.0] - 2026-01-26

### 🔨 Fase Inicial de Desarrollo

#### ✨ Agregado

- Estructura básica del proyecto Spring Boot
- Entidad `Price` con campos de dominio
- Controlador REST inicial
- Repositorio JPA básico
- Excepciones de dominio (`PriceNotFoundException`)

#### 🐛 Corregido

- Validaciones de parámetros nulos en controlador
- Manejo de excepciones mejorado
- Formato de respuestas consistente

---

## Notas sobre Versiones Futuras

### v1.1.0 (Próxima)

Características planeadas:
- [ ] Caché en memoria para precios consultados frecuentemente
- [ ] Búsqueda avanzada con filtros adicionales
- [ ] Auditoría de cambios de precios
- [ ] API de administración para gestionar precios

### v2.0.0 (Futuro)

Mejoras mayores planeadas:
- [ ] Soporte para múltiples monedas
- [ ] Sistema de descuentos por volumen
- [ ] Predicción de demanda basada en ML
- [ ] GraphQL como alternativa a REST

---

## Guía de Contribución

Para detalles sobre cómo contribuir, ver [CONTRIBUTING.md](./CONTRIBUTING.md)

Resumen rápido:
- Usa commits semánticos: `feat:`, `fix:`, `docs:`, etc.
- Crea ramas: `feature/`, `bugfix/`, `hotfix/`
- PR con descripción clara
- Tests pasando es obligatorio

---

## Historial Detallado

Para ver el historial completo de commits:
```bash
git log --oneline
git log --format="%H %s %b"
```

---

**Última actualización**: 7 de febrero de 2026  
**Versión actual**: 1.0.0  
**Estado**: Productivo ✅
