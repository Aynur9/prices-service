# Guía de Contribución - Prices Service

Gracias por tu interés en contribuir a **Prices Service**. Este documento describe los estándares y procesos que debemos seguir para mantener la calidad y consistencia del proyecto.

## 📋 Tabla de Contenidos

- [Estándares de Commits](#estándares-de-commits)
- [Flujo de Trabajo con Ramas](#flujo-de-trabajo-con-ramas)
- [Pull Requests](#pull-requests)
- [Convenciones de Código](#convenciones-de-código)
- [Testing](#testing)
- [Versionado](#versionado)

---

## 🔤 Estándares de Commits

Utilizamos **Conventional Commits** para mantener un historial claro y automatizable.

### Formato

```
<tipo>(<alcance>): <descripción>

<cuerpo>

<pie de página>
```

### Tipos de Commit

- **`feat`**: Nueva característica
  ```
  feat(prices): add pagination for high-load scenarios
  ```

- **`fix`**: Corrección de bug
  ```
  fix(controller): handle null parameters validation
  ```

- **`docs`**: Cambios en documentación
  ```
  docs(readme): update installation instructions
  ```

- **`style`**: Cambios sin afectar la lógica (formatting, missing semicolons, etc)
  ```
  style(repository): format code with correct indentation
  ```

- **`refactor`**: Cambio en el código sin añadir funcionalidad ni corregir bugs
  ```
  refactor(domain): extract price validation to separate method
  ```

- **`perf`**: Mejoras de rendimiento
  ```
  perf(query): optimize price search with index on brandId
  ```

- **`test`**: Agregar o actualizar tests
  ```
  test(controller): add validation tests for null parameters
  ```

- **`chore`**: Cambios en build, dependencias, CI/CD
  ```
  chore(maven): update Spring Boot to 3.4.0
  ```

### Alcance (Scope)

Describe qué parte del proyecto se modifica:
- `controller`, `service`, `repository`, `domain`, `dto`, `mapper`, `config`, `exception`

### Descripción

- Usa **imperativo** presente: "add" no "added" o "adds"
- No empieces con mayúscula
- Sin punto final
- Máximo 50 caracteres

### Cuerpo (Opcional)

- Explica **QUÉ** cambió y **POR QUÉ**, no cómo
- Máximo 72 caracteres por línea
- Separa del título con línea en blanco

### Pie de Página (Opcional)

Para referencias a issues:
```
Fixes #123
Closes #456
Related-to #789
```

### Ejemplos Completos

```
feat(prices): add projection query for high-load scenarios

Implement findApplicableProjected() method in PriceRepositoryImpl
to support paginated queries with DTO projection for better
performance in high-concurrency environments.

Uses Spring Data JPA @Query with explicit column selection
to reduce memory footprint and database load.

Fixes #42
```

```
fix(controller): handle missing request parameters

Add MissingServletRequestParameterException handler in
GlobalExceptionHandler to return 400 Bad Request instead
of 500 Internal Server Error when required parameters
are not provided.

Fixes #15
```

---

## 🌿 Flujo de Trabajo con Ramas

### Estructura de Ramas

```
main (producción)
  ↑
develop (integración)
  ↑
feature/*, bugfix/*, hotfix/*
```

### Convención de Nombres

- **Feature**: `feature/add-pagination`
- **Bugfix**: `bugfix/null-parameter-validation`
- **Hotfix**: `hotfix/critical-security-issue`
- **Docs**: `docs/api-documentation`

### Proceso

1. **Crear rama desde `develop`**
   ```bash
   git checkout develop
   git pull origin develop
   git checkout -b feature/your-feature-name
   ```

2. **Hacer cambios y commits**
   ```bash
   git commit -m "feat(scope): description"
   ```

3. **Enviar rama**
   ```bash
   git push origin feature/your-feature-name
   ```

4. **Crear Pull Request** a `develop`

5. **Merge a `develop` tras aprobación**

6. **Periodic merge de `develop` a `main` para releases**

---

## 📥 Pull Requests

### Antes de Crear un PR

- [ ] Los tests pasan: `mvn -B test`
- [ ] El código compila: `mvn clean compile`
- [ ] Seguiste los estándares de commits
- [ ] Actualizaste documentación si es necesario
- [ ] No hay conflictos con `develop`

### Template de PR

```markdown
## 📝 Descripción
Breve descripción de los cambios

## 🎯 Objetivo
Qué problema resuelve o qué característica agrega

## 📋 Cambios
- [ ] Cambio 1
- [ ] Cambio 2
- [ ] Cambio 3

## 🧪 Testing
Cómo se probó:
- [ ] Tests unitarios added/updated
- [ ] Tests de integración passed

## 📸 Screenshots (si aplica)
Adjunta screenshots de cambios visuales

## 🔗 Issues Relacionados
Fixes #123, Related-to #456

## ✅ Checklist
- [ ] Mi código sigue los estándares del proyecto
- [ ] He actualizado la documentación
- [ ] He añadido tests para mis cambios
- [ ] Todos los tests pasan
- [ ] No hay cambios innecesarios
```

### Code Review

Todo PR requiere **al menos 1 aprobación** antes de merge.

---

## 🔧 Convenciones de Código

### Java

- **Naming**: camelCase para variables/métodos, PascalCase para clases
- **Imports**: Agrupa por: JDK, Spring, Project, Others
- **Line length**: Máximo 120 caracteres
- **Javadoc**: En métodos públicos de services y controllers

```java
/**
 * Obtiene el precio aplicable para un producto en una fecha.
 *
 * @param brandId identificador de la cadena
 * @param productId identificador del producto
 * @param date fecha de consulta
 * @return precio aplicable
 * @throws PriceNotFoundException si no existe precio
 */
public Price get(Long brandId, Long productId, LocalDateTime date) {
    // implementation
}
```

### Estructura de Paquetes

```
com.zara.prices
├── application      (casos de uso)
├── domain           (entidades, lógica de negocio)
│   ├── model
│   ├── port         (interfaces)
│   └── service
├── infrastructure   (implementaciones técnicas)
│   ├── configuration
│   ├── persistence  (repositorios, mappers)
│   └── web          (controllers, exceptions, DTOs)
└── DemoApplication
```

---

## 🧪 Testing

### Cobertura Mínima

- Servicios de aplicación: 90%+
- Lógica de dominio: 95%+
- Controladores: 80%+

### Ejecución de Tests

```bash
# Todos los tests
mvn -B test

# Un test específico
mvn -B test -Dtest=PriceControllerTest

# Con cobertura
mvn clean test jacoco:report
```

### Estándares de Tests

- Usa JUnit 5 y Mockito
- Nombre: `<Clase>Test`
- Método de test: `test<Escenario>` o `<Escenario>ShouldWork`

```java
@Test
void testGetPriceShouldReturnValidPrice() {
    // Given
    Long brandId = 1L;
    Long productId = 35455L;
    
    // When
    Price result = service.get(brandId, productId, date);
    
    // Then
    assertThat(result.getPrice()).isEqualTo(expectedPrice);
}
```

---

## 🏷️ Versionado

Usamos **Semantic Versioning**: `MAJOR.MINOR.PATCH`

- **MAJOR**: Cambios incompatibles
- **MINOR**: Nueva funcionalidad (backward compatible)
- **PATCH**: Correcciones

### Crear un Release

1. Asegúrate de que todos los tests pasen
2. Actualiza `CHANGELOG.md`
3. Crea tag:
   ```bash
   git tag -a v1.0.0 -m "Release version 1.0.0"
   git push origin v1.0.0
   ```

---

## ❓ Preguntas Frecuentes

**P: ¿Puedo commitear directamente a `main`?**  
R: No. Siempre crea una rama feature y un PR para revisión.

**P: ¿Qué hago si necesito actualizar mi rama con cambios de `develop`?**  
R: Usa rebase (preferido) o merge:
```bash
git fetch origin
git rebase origin/develop
```

**P: ¿Cuál es el mínimo para un PR?**  
R: Tests pasando + mensaje de commit descriptivo + 1 aprobación.

---

## 📚 Recursos

- [Conventional Commits](https://www.conventionalcommits.org/)
- [Semantic Versioning](https://semver.org/)
- [Git Branching Model](https://nvie.com/posts/a-successful-git-branching-model/)

---

**Gracias por seguir estos estándares. Mantienen el proyecto profesional y colaborativo.** 🚀
