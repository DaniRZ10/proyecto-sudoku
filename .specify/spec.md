# Especificación: Proyecto Sudoku

## 1. Outcome (Qué se busca)

Una aplicación Java de escritorio que permita jugar al Sudoku clásico (cuadrícula 9×9), con tres niveles de dificultad, validación de movimientos en tiempo real y verificación automática del estado resuelto.

## 2. Scope

### Dentro de alcance
- Generación de tableros válidos con dificultades **fácil**, **medio** y **difícil**.
- Validación de movimientos según reglas (fila, columna, bloque 3×3).
- Modo consola (CLI) interactivo.
- Modo gráfico (GUI) con Swing.
- Tests unitarios JUnit del paquete `core`.
- Documentación Javadoc auto-generada y desplegada en GitHub Pages.
- CI/CD con GitHub Actions.

### Fuera de alcance
- Persistencia de partidas (guardar/cargar).
- Multijugador online.
- Pistas (hints) automáticas.
- Resolución asistida del tablero por parte del programa para el usuario.
- Ranking, estadísticas o tiempos.

## 3. Constraints

- **Lenguaje:** Java 25 LTS.
- **Build tool:** Apache Maven.
- **Identificadores en inglés** (ver Constitución III).
- **GUI con Swing** (no JavaFX) por reproducibilidad en CI.
- **Sin dependencias externas** más allá de JUnit Jupiter (testing) y JaCoCo (cobertura).

## 4. Prior Decisions

- **Estructura de paquetes:** `com.drios.sudoku.{core, game, ui}`.
- **Tablero como `int[][]`** (sin abstracción excesiva). El valor `0` representa celda vacía.
- **Generación mediante backtracking** sobre un tablero base aleatorio.
- **Dificultad determina celdas vacías:**
  - `EASY` → 36 celdas vacías
  - `MEDIUM` → 46 celdas vacías
  - `HARD` → 54 celdas vacías
- **Enum `Difficulty`** para representar niveles (no Strings sueltos).

## 5. User Stories

### US-01 — Iniciar partida en consola
*Como jugador, quiero ejecutar el programa y elegir dificultad para empezar a jugar en consola.*

### US-02 — Colocar números
*Como jugador, quiero introducir fila, columna y valor para colocar un número en el tablero.*

### US-03 — Recibir feedback de movimientos inválidos
*Como jugador, quiero recibir un mensaje claro si intento colocar un número que viola las reglas.*

### US-04 — Detectar tablero resuelto
*Como jugador, quiero que el sistema me notifique automáticamente cuando completo el tablero correctamente.*

### US-05 — Jugar con interfaz gráfica
*Como jugador, quiero una alternativa visual con Swing donde pueda hacer clic en celdas e introducir valores.*

## 6. Verification Criteria

- Todos los tests JUnit pasan en GitHub Actions.
- Cobertura ≥ 80% en `com.drios.sudoku.core`.
- Javadoc se genera sin warnings y se publica en GitHub Pages.
- El programa se ejecuta sin excepciones en los flujos principales (CLI y GUI).
- El diagrama Mermaid de clases refleja el código real (verificable manualmente).
- El repositorio sigue GitFlow: `main` solo recibe merges desde `release/*` o `hotfix/*`.
