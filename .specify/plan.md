# Plan Técnico: Proyecto Sudoku

## Stack

- **Java 25 LTS** (Temurin)
- **Maven 3.9+**
- **JUnit Jupiter 5.10+**
- **JaCoCo 0.8.x** (cobertura)
- **Swing** (UI)
- **GitHub Actions** (CI/CD)
- **GitHub Pages** (hosting de Javadoc)

## Estructura de paquetes

```
com.drios.sudoku
├── Main                  -> Entry point (CLI por defecto, --gui para Swing)
├── core
│   ├── SudokuBoard       -> Estado y validación del tablero
│   ├── SudokuGenerator   -> Generación con backtracking
│   ├── Difficulty        -> Enum: EASY, MEDIUM, HARD
│   ├── InvalidMoveException
│   └── BoardGenerationException
├── game
│   └── SudokuGame        -> Loop interactivo en consola
└── ui
    └── SudokuGUI         -> JFrame con grid 9x9
```

## Estructura del repositorio

```
proyecto-sudoku/
├── .github/workflows/ci.yml       -> Pipeline CI/CD
├── .specify/                      -> Constitución, spec, plan, tasks
├── docs/architecture/             -> Diagramas Mermaid
├── src/main/java/com/drios/sudoku/
├── src/test/java/com/drios/sudoku/
├── pom.xml
├── .gitignore
└── README.md
```

## Branching (GitFlow)

| Rama | Propósito | Origen | Destino |
|---|---|---|---|
| `main` | Releases estables (protegida) | — | — |
| `dev` | Integración continua | `main` | — |
| `feature/*` | Trabajo en curso | `dev` | `dev` (vía PR) |
| `release/vX.Y.Z` | Cierre de versión | `dev` | `main` + `dev` |
| `hotfix/*` | Parches urgentes | `main` | `main` + `dev` |

## Workflows de GitHub Actions (`ci.yml`)

Tres jobs encadenados:

1. **`test`** — Se dispara en push/PR a cualquier rama.
   - Checkout
   - Setup JDK 25 (Temurin)
   - `mvn -B clean verify` (compila + tests + JaCoCo)
   - Sube reporte de cobertura como artefacto

2. **`javadoc`** — Solo en `main`. Depende de `test`.
   - Genera Javadoc con `mvn javadoc:javadoc`
   - Sube como artefacto de Pages

3. **`deploy-pages`** — Solo en `main`. Depende de `javadoc`.
   - Despliega a GitHub Pages

## Convenciones de código

- Clases en `PascalCase`, métodos y variables en `camelCase`, constantes en `UPPER_SNAKE_CASE`.
- Excepciones de dominio: `InvalidMoveException` (RuntimeException, para movimientos ilegales en runtime), `BoardGenerationException` (RuntimeException, para fallos del generador).
- Sin `null` en API pública: usar `Optional<T>` cuando aplique.
- `final` en parámetros y campos cuando sea posible.

## Testing

- Tests bajo `src/test/java` con misma estructura de paquetes que el código.
- **Naming:** `methodName_should_expectedBehavior_when_stateUnderTest`.
- Cobertura medida con JaCoCo, umbral mínimo 80% en `core`.
- Para tests de I/O (CLI), inyectar `PrintStream` y leer con `ByteArrayInputStream`.

## Documentación

- Javadoc en todos los métodos públicos.
- `docs/architecture/uml-classes.md` con diagrama Mermaid de clases y flujo de actividad.
- `README.md` con resumen, instrucciones de build/run, badge de CI y enlace a Javadoc en Pages.

## Dependencias externas

Solo dependencias declaradas en `pom.xml`:
- `org.junit.jupiter:junit-jupiter` (test scope).

Plugins Maven:
- `maven-compiler-plugin` 3.13.0
- `maven-surefire-plugin` 3.2.5
- `maven-javadoc-plugin` 3.6.3
- `jacoco-maven-plugin` 0.8.12
