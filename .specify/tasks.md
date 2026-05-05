# Tasks Breakdown — Proyecto Sudoku

> Cada tarea genera **exactamente UN commit**. Formato: `T0XX: <descripción imperativa en inglés>`.
> Las tareas marcadas con `[P]` pueden ejecutarse en paralelo (no tienen dependencias mutuas).
> Antes de cada commit, el agente solicita confirmación al autor.

---

## Phase 0 — Scaffolding (rama: `dev`)

### T001 — Verify project skeleton
- Verificar que `pom.xml`, `src/main/java`, `src/test/java` existen.
- Ejecutar `mvn clean compile` y `mvn test` (sin tests todavía debe pasar igualmente).
- **Sin commit** (verificación inicial únicamente).

### T002 — Define Difficulty enum
- Crear `com.drios.sudoku.core.Difficulty` con valores `EASY`, `MEDIUM`, `HARD`.
- Cada valor expone `getEmptyCellsCount()` (36, 46, 54 respectivamente).
- Javadoc completo.
- **Commit:** `T002: Add Difficulty enum with empty cells configuration`

### T003 — Add domain exceptions [P con T002]
- Crear `InvalidMoveException` y `BoardGenerationException` (ambas `RuntimeException`).
- Constructores con mensaje y con mensaje + causa.
- Javadoc completo.
- **Commit:** `T003: Add domain exceptions for board operations`

---

## Phase 1 — Core Domain (rama: `feature/core-domain`, base: `dev`)

### T004 — SudokuBoard skeleton (TDD)
- Crear test class `SudokuBoardTest` con casos para constructor, `getValue`, `setValue`, `isCellFixed`.
- Implementar `SudokuBoard` con `int[][] board` (9×9) y `boolean[][] fixedCells`.
- Constructor que inicializa todo a 0 y a `false`.
- Javadoc completo.
- **Commit:** `T004: Implement SudokuBoard skeleton with state accessors`

### T005 — Implement isMovementValid (TDD)
- Tests: violación de fila, violación de columna, violación de bloque 3×3, movimiento válido, valor fuera de rango (0-9), celda fija, coordenadas fuera de rango.
- Implementar `isMovementValid(int row, int column, int value)` en `SudokuBoard`.
- **Commit:** `T005: Implement isMovementValid with row/column/box rules`

### T006 — Implement placeNumber (TDD)
- Tests: colocación legal actualiza el tablero; colocación ilegal lanza `InvalidMoveException`; celdas fijas rechazan modificación.
- Implementar `placeNumber(int row, int column, int value)`.
- **Commit:** `T006: Implement placeNumber with validation guard`

### T007 — Implement isSolved (TDD)
- Tests: tablero vacío → false; tablero válido completo → true; tablero con conflictos → false; tablero incompleto → false.
- Implementar `isSolved()`.
- **Commit:** `T007: Implement isSolved board completion check`

### T008 — Implement printBoard (TDD)
- Sobrecargar para aceptar un `PrintStream` inyectable (testabilidad).
- Tests usando `ByteArrayOutputStream` para verificar el formato de salida.
- **Commit:** `T008: Implement printBoard with injectable PrintStream`

### T009 — Sync UML diagram for core
- Actualizar `docs/architecture/uml-classes.md` con todas las clases del paquete `core`.
- **Commit:** `T009: Sync UML diagram with core package`

### T010 — PR core-domain → dev (acción humana)
- El agente prepara el cuerpo del PR (resumen de cambios, tareas cubiertas, cómo probar) y lo deja listo.
- El **humano** abre el PR en la web y lo mergea tras revisión.

---

## Phase 2 — Generator (rama: `feature/generator`, base: `dev`)

### T011 — Backtracking solver helper (TDD)
- Test: dado un tablero parcialmente lleno, lo completa o devuelve `false` si es imposible.
- Implementar método privado `solve(int[][] board)` en `SudokuGenerator`.
- **Commit:** `T011: Implement backtracking solver for board generation`

### T012 — Generate full valid board (TDD)
- Test: el tablero generado pasa `isSolved()`.
- Test: dos generaciones consecutivas producen tableros distintos (aleatoriedad).
- Implementar `generateFullBoard()`.
- **Commit:** `T012: Generate fully solved board via randomized backtracking`

### T013 — Carve out cells per difficulty (TDD)
- Test: `EASY` → exactamente 36 celdas vacías; `MEDIUM` → 46; `HARD` → 54.
- Implementar `generateBoard(Difficulty difficulty)` que devuelve un `SudokuBoard` listo para jugar.
- **Commit:** `T013: Implement difficulty-based cell carving`

### T014 — Mark fixed cells correctly (TDD)
- Test: las celdas pre-rellenadas en el tablero generado están marcadas como fijas.
- Test: las celdas vacías NO están marcadas como fijas.
- **Commit:** `T014: Mark generated cells as fixed in SudokuBoard`

### T015 — Sync UML diagram for generator
- **Commit:** `T015: Sync UML diagram with SudokuGenerator`

### T016 — PR generator → dev (acción humana)

---

## Phase 3 — CLI Game (rama: `feature/cli-game`, base: `dev`)

### T017 — SudokuGame loop skeleton
- Implementar clase `SudokuGame` con método `start()`.
- Lectura de input via `Scanner` (inyectable para testing).
- Selección de dificultad inicial.
- **Commit:** `T017: Implement SudokuGame loop skeleton with difficulty selection`

### T018 — Move loop with feedback (TDD parcial)
- En cada iteración: imprimir tablero, leer (fila, columna, valor), validar, colocar o mostrar mensaje de error.
- Test: simular input via `ByteArrayInputStream` para un par de movimientos.
- **Commit:** `T018: Implement interactive move loop with input validation`

### T019 — Win condition handling
- Detectar `isSolved()` tras cada movimiento y terminar con mensaje de éxito.
- Permitir comando `quit` para abandonar la partida.
- **Commit:** `T019: Detect solved state and end game gracefully`

### T020 — Wire Main entry point
- Crear `com.drios.sudoku.Main` con `main(String[] args)`.
- Por defecto lanza `SudokuGame`. Si args contiene `--gui`, lanza `SudokuGUI`.
- **Commit:** `T020: Wire Main entry point with CLI/GUI dispatch`

### T021 — PR cli-game → dev (acción humana)

---

## Phase 4 — Swing GUI (rama: `feature/swing-gui`, base: `dev`)

### T022 — SudokuGUI window skeleton
- `JFrame` con grid 9×9 de `JTextField`.
- Selector de dificultad (`JComboBox<Difficulty>`).
- Botón "New Game".
- **Commit:** `T022: Implement SudokuGUI window skeleton with 9x9 grid`

### T023 — Wire generation to GUI
- Botón "New Game" llama a `SudokuGenerator.generateBoard(...)` y renderiza el resultado.
- Celdas fijas no editables (`setEditable(false)`) y con fondo gris claro.
- Celdas libres editables con fondo blanco.
- **Commit:** `T023: Wire board generation to GUI new-game flow`

### T024 — Real-time validation
- `DocumentListener` (o equivalente) en cada celda libre.
- Al cambiar valor, llama a `isMovementValid` y colorea fondo rojo si es inválido.
- **Commit:** `T024: Add real-time cell validation with visual feedback`

### T025 — Solved state notification
- Tras cada cambio, comprobar `isSolved()`. Si true, mostrar `JOptionPane` con mensaje de victoria.
- **Commit:** `T025: Show victory dialog on solved board`

### T026 — Sync UML diagram for ui
- **Commit:** `T026: Sync UML diagram with SudokuGUI`

### T027 — PR swing-gui → dev (acción humana)

---

## Phase 5 — Polish & Release (rama: `release/v1.0.0`, base: `dev`)

### T028 — JaCoCo coverage check
- Ejecutar `mvn clean verify`. Revisar reporte en `target/site/jacoco/`.
- Si la cobertura del paquete `core` está por debajo del 80%, añadir tests hasta superarlo.
- **Commit:** `T028: Reach 80% coverage threshold on core package`

### T029 — Javadoc clean build
- `mvn javadoc:javadoc`. Revisar warnings.
- Corregir Javadocs incompletos hasta que el build sea limpio.
- **Commit:** `T029: Resolve Javadoc warnings across public API`

### T030 — Update README with badges and usage
- Badge de CI funcionando con la URL real del repo.
- Sección "How to run" para CLI y GUI.
- Enlace a Javadoc en GitHub Pages.
- **Commit:** `T030: Update README with badges and usage instructions`

### T031 — Final UML review
- Verificar coherencia total entre código y diagrama Mermaid.
- **Commit:** `T031: Final UML diagram review`

### T032 — PR release/v1.0.0 → main (acción humana)
- El humano abre PR `release/v1.0.0` → `main` y mergea.
- Tras merge, crear tag `v1.0.0` desde la web.
- GitHub Actions desplegará Javadoc a Pages automáticamente.

### T033 — Backmerge release → dev (acción humana)
- PR `release/v1.0.0` → `dev` para mantener `dev` alineada.

---

## Resumen de commits esperados

Si todo sale en orden, el repo terminará con aproximadamente **30 commits T0XX** distribuidos en 5 ramas feature + 1 rama release, más los merges a `dev` y `main`.
