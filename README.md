# Fibonacci API - Quarkus

API REST para calcular números de Fibonacci con sistema de caché en base de datos y estadísticas de consultas.

## Tecnologías

- **Framework**: Quarkus 3.28.1
- **Lenguaje**: Java 21
- **ORM**: Hibernate ORM con Panache
- **Base de Datos**: H2 (desarrollo) / PostgreSQL (producción)
- **Testing**: JUnit 5, RestAssured, JaCoCo (83% coverage)

## Características

- Cálculo de Fibonacci con algoritmo iterativo optimizado (BigInteger)
- Sistema de caché en base de datos
- Estadísticas de números más consultados
- 24 tests automatizados con 83% de cobertura

## Demo en la Nube

**URL:** https://fibonacci-quarkus.onrender.com

**Ejemplos:**
- Fibonacci: https://fibonacci-quarkus.onrender.com/api/fibonacci/10
- Estadísticas: https://fibonacci-quarkus.onrender.com/api/fibonacci/stats
- Health: https://fibonacci-quarkus.onrender.com/api/fibonacci/health

**Nota:** Primera petición puede tardar aproximadamente 30 segundos.

## Ejecutar Localmente

```bash
# Desarrollo
mvn quarkus:dev

# Tests con coverage
mvn verify
```

## Cobertura de Tests

- **Tests:** 24 tests
- **Cobertura:** 83%
- **Reporte:** Ejecutar `mvn verify` y abrir `target/site/jacoco/index.html`

**Desglose por paquete:**
- service: 100%
- entity: 100%
- dto: 100%
- resource: 100%
- exception: 38%

## Endpoints

### Calcular Fibonacci

```bash
GET /api/fibonacci/{n}
```

**Respuesta:**
```json
{
  "n": 10,
  "value": "55",
  "fromCache": false
}
```

### Estadísticas (Top N)

```bash
GET /api/fibonacci/stats?limit={limit}
```

**Respuesta:**
```json
[
  {
    "n": 10,
    "queryCount": 5,
    "lastAccessed": "2025-10-01T14:30:00"
  }
]
```

### Todas las Estadísticas

```bash
GET /api/fibonacci/stats/all
```

### Health Check

```bash
GET /api/fibonacci/health
```

## Configuración

### Perfiles

- **dev**: H2 in-memory, puerto 8084
- **prod**: PostgreSQL, puerto 8080

### Variables de Entorno (Producción)

```bash
DATABASE_URL=jdbc:postgresql://localhost:5432/fibonaccidb
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=tu_password
```

## Arquitectura

```
REST Resource (JAX-RS)
       ↓
Service Layer (CDI)
       ↓
Panache Entities (Active Record)
       ↓
Database (H2/PostgreSQL)
```

## Algoritmo

Implementación iterativa O(n) con `BigInteger`:

```java
private String computeFibonacci(Integer n) {
    if (n == 0) return "0";
    if (n == 1 || n == 2) return "1";

    BigInteger prev = BigInteger.ONE;
    BigInteger current = BigInteger.ONE;

    for (int i = 3; i <= n; i++) {
        BigInteger next = prev.add(current);
        prev = current;
        current = next;
    }

    return current.toString();
}
```
