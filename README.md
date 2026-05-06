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
# Compilar, ejecutar tests y verificar calidad
mvn clean verify

# Ejecutar modo consola (CLI)
mvn exec:java

# Ejecutar modo gráfico (Swing GUI)
mvn exec:java -Dexec.args="--gui"
```

## Guía de Juego

### Modo Consola (CLI)
1. Al iniciar, selecciona la dificultad (1-3).
2. Para realizar un movimiento, introduce: `fila columna valor` (ejemplo: `0 0 5` para poner un 5 en la esquina superior izquierda).
3. Escribe `quit` en cualquier momento para abandonar la partida.
4. El juego te avisará si el movimiento viola las reglas del Sudoku.

### Modo Gráfico (GUI)
1. Selecciona la dificultad en el desplegable superior y pulsa **"New Game"**.
2. Haz clic en cualquier celda blanca y escribe un número (1-9).
3. **Feedback visual:**
   - Celda Blanca: Movimiento válido.
   - Celda Roja: Movimiento inválido (viola reglas de fila, columna o bloque) o entrada no válida.
   - Celda Gris: Pista inicial (fija, no editable).

## Documentación

- 📖 [Javadoc en GitHub Pages](https://danirz10.github.io/proyecto-sudoku/)
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
