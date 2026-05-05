package com.drios.sudoku.core;

/**
 * Represents the state of a 9×9 Sudoku board.
 *
 * <p>Cells are identified by zero-based (row, column) coordinates.
 * An empty cell holds the value {@code 0}. Fixed cells are those
 * pre-filled during board generation and cannot be modified by the player.</p>
 *
 * <p>This class is intentionally kept as a plain data holder with accessors;
 * validation and game logic live in separate classes to respect SRP.</p>
 */
public class SudokuBoard {

    /** Size of one side of the Sudoku grid. */
    public static final int SIZE = 9;

    /** Sentinel value representing an empty cell. */
    public static final int EMPTY_CELL = 0;

    /** Current numeric values of every cell. 0 means empty. */
    private final int[][] board;

    /** Tracks which cells are fixed (pre-filled) and may not be changed by the player. */
    private final boolean[][] fixedCells;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Creates a new, empty Sudoku board.
     *
     * <p>All cells are initialised to {@link #EMPTY_CELL} ({@code 0})
     * and none are marked as fixed.</p>
     */
    public SudokuBoard() {
        this.board = new int[SIZE][SIZE];
        this.fixedCells = new boolean[SIZE][SIZE];
        // Java initialises int[][] to 0 and boolean[][] to false by default,
        // so no explicit loop is required here.
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    /**
     * Returns the numeric value stored in the given cell.
     *
     * @param row    zero-based row index (0–8)
     * @param column zero-based column index (0–8)
     * @return the cell value (1–9), or {@link #EMPTY_CELL} (0) if the cell is empty
     */
    public int getValue(final int row, final int column) {
        return board[row][column];
    }

    /**
     * Sets the value of a cell and optionally marks it as fixed.
     *
     * <p>This method is intended for use by {@code SudokuGenerator} when building
     * the initial puzzle. Player moves should go through
     * {@code placeNumber(int, int, int)} which enforces validation.</p>
     *
     * @param row    zero-based row index (0–8)
     * @param column zero-based column index (0–8)
     * @param value  the value to store (0–9)
     * @param fixed  {@code true} if this cell should be treated as a pre-filled,
     *               immutable clue; {@code false} otherwise
     */
    public void setValue(final int row, final int column, final int value, final boolean fixed) {
        board[row][column] = value;
        fixedCells[row][column] = fixed;
    }

    /**
     * Indicates whether the given cell is a pre-filled clue that the player
     * may not modify.
     *
     * @param row    zero-based row index (0–8)
     * @param column zero-based column index (0–8)
     * @return {@code true} if the cell is fixed; {@code false} otherwise
     */
    public boolean isCellFixed(final int row, final int column) {
        return fixedCells[row][column];
    }
}
