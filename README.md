# Proyecto Sudoku

[![CI](https://github.com/DaniRZ10/proyecto-sudoku/actions/workflows/ci.yml/badge.svg)](https://github.com/DaniRZ10/proyecto-sudoku/actions/workflows/ci.yml)

Aplicación de escritorio en Java que implementa el clásico Sudoku 9×9 con tres niveles de dificultad, modo consola y modo gráfico (Swing).

## Stack

- Java 25 LTS
- Maven 3.9+
- JUnit Jupiter 5
- JaCoCo (cobertura)
- Swing (UI)
- GitHub Actions (CI/CD)
- GitHub Pages (Javadoc hosting)

## Cómo ejecutar

```bash
# Compilar y ejecutar tests
mvn clean verify

# Ejecutar modo consola (CLI)
mvn exec:java

# Ejecutar modo GUI (Swing)
mvn exec:java -Dexec.args="--gui"
```

## Documentación

- 📖 [Javadoc en GitHub Pages](https://DaniRZ10.github.io/proyecto-sudoku/)
- 🗺️ [Diagrama UML de clases](docs/architecture/uml-classes.md)
- 📋 [Especificación del proyecto](.specify/spec.md)
- 🛠️ [Plan técnico](.specify/plan.md)
- 📜 [Constitución del proyecto](.specify/constitution.md)
- ✅ [Tareas (T001–T033)](.specify/tasks.md)

## Estructura del repositorio

```
proyecto-sudoku/
├── .github/workflows/   -> CI/CD
├── .specify/            -> SDD: constitution, spec, plan, tasks
├── docs/architecture/   -> Diagramas Mermaid
├── src/main/java/       -> Código fuente (com.drios.sudoku)
└── src/test/java/       -> Tests JUnit
```

## Metodología

Este proyecto se desarrolla bajo **Spec-Driven Development (SDD)**. El flujo es:

1. **Constitución** (`.specify/constitution.md`) — principios no negociables.
2. **Especificación** (`.specify/spec.md`) — qué se construye.
3. **Plan técnico** (`.specify/plan.md`) — cómo se construye.
4. **Tareas** (`.specify/tasks.md`) — desglose ejecutable, una tarea = un commit.

Las ramas siguen **GitFlow estricto**: `main` (releases) ← `release/*` ← `dev` ← `feature/*`. La rama `main` está protegida y solo recibe merges vía Pull Request.

## Autor

Daniel Ríos Zea
