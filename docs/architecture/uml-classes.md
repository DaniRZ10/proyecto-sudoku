# Diagrama UML — Proyecto Sudoku

> Este documento se actualiza en el mismo commit que cualquier cambio en signatures públicas (clases, métodos públicos, enums).

## Diagrama de clases (estado objetivo)

```mermaid
classDiagram
    class Main {
        +main(args: String[]) void
        -launchCli() void
        -launchGui() void
    }

    class Difficulty {
        <<enumeration>>
        EASY
        MEDIUM
        HARD
        +getEmptyCellsCount() int
    }

    class SudokuBoard {
        -board: int[9][9]
        -fixedCells: boolean[9][9]
        +SudokuBoard()
        +getValue(row: int, column: int) int
        +setValue(row: int, column: int, value: int, fixed: boolean) void
        +isCellFixed(row: int, column: int) boolean
        +isMovementValid(row: int, column: int, value: int) boolean
        +placeNumber(row: int, column: int, value: int) void
        +isSolved() boolean
        +printBoard() void
        +printBoard(ps: PrintStream) void
    }

    class SudokuGenerator {
        +generateBoard(difficulty: Difficulty) SudokuBoard
        ~generateFullBoard() int[9][9]
        ~solve(board: int[9][9]) boolean
        -carveCells(board: int[9][9], emptyCount: int) void
        -isSafe(board: int[9][9], row: int, col: int, digit: int) boolean
    }

    class SudokuGame {
        -board: SudokuBoard
        -in: Scanner
        -out: PrintStream
        +SudokuGame(in: Scanner, out: PrintStream)
        +start() void
        -selectDifficulty() Difficulty
        -play() void
    }

    class SudokuGUI {
        -frame: JFrame
        -cells: JTextField[9][9]
        -difficultyCombo: JComboBox
        -newGameButton: JButton
        -generator: SudokuGenerator
        -board: SudokuBoard
        -isRendering: boolean
        +SudokuGUI()
        +launch() void
        -startNewGame() void
        -renderBoard() void
        -validateCell(r: int, c: int) void
    }

    class InvalidMoveException {
        <<exception>>
    }

    class BoardGenerationException {
        <<exception>>
    }

    Main ..> SudokuGame : creates
    Main ..> SudokuGUI : creates
    SudokuGame --> SudokuBoard
    SudokuGame ..> SudokuGenerator
    SudokuGUI --> SudokuBoard
    SudokuGUI ..> SudokuGenerator
    SudokuGenerator ..> Difficulty
    SudokuGenerator ..> SudokuBoard : produces
    SudokuBoard ..> InvalidMoveException : throws
    SudokuGenerator ..> BoardGenerationException : throws
```

## Diagrama de actividad (flujo de juego en CLI)

```mermaid
flowchart TD
    Start([Inicio]) --> ChooseDifficulty[Elegir dificultad]
    ChooseDifficulty --> Generate[Generar tablero]
    Generate --> Print[Mostrar tablero]
    Print --> Input{¿Movimiento?}
    Input -->|Sí| Validate{¿Movimiento válido?}
    Validate -->|Sí| Place[Colocar número]
    Validate -->|No| Error[Mostrar error]
    Error --> Print
    Place --> CheckWin{¿Tablero resuelto?}
    CheckWin -->|No| Print
    CheckWin -->|Sí| Win([Victoria])
    Input -->|quit| Quit([Salir])
```

## Diagrama de estados del tablero

```mermaid
stateDiagram-v2
    [*] --> Empty: new SudokuBoard()
    Empty --> Generated: SudokuGenerator.generateBoard()
    Generated --> InProgress: placeNumber() válido
    InProgress --> InProgress: placeNumber() válido
    InProgress --> Solved: isSolved() == true
    Solved --> [*]
```
