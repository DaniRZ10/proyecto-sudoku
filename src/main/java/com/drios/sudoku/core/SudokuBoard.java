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

    // -------------------------------------------------------------------------
    // Validation — T005
    // -------------------------------------------------------------------------

    /**
     * Determines whether placing {@code value} at ({@code row}, {@code column})
     * is a legal Sudoku move.
     *
     * <p>A move is considered valid when ALL of the following hold:</p>
     * <ul>
     *   <li>Row and column are within the 0–8 range.</li>
     *   <li>Value is between 1 and 9 (inclusive).</li>
     *   <li>The target cell is not fixed.</li>
     *   <li>The value does not already appear in the same row.</li>
     *   <li>The value does not already appear in the same column.</li>
     *   <li>The value does not already appear in the same 3×3 box.</li>
     * </ul>
     *
     * <p>When checking row/column/box conflicts, the target cell itself is
     * excluded so that re-placing the same value on its own non-fixed cell
     * is considered valid.</p>
     *
     * @param row    zero-based row index (0–8)
     * @param column zero-based column index (0–8)
     * @param value  the digit to place (1–9)
     * @return {@code true} if the move is legal; {@code false} otherwise
     */
    public boolean isMovementValid(final int row, final int column, final int value) {
        if (!isInBounds(row, column)) {
            return false;
        }
        if (value < 1 || value > SIZE) {
            return false;
        }
        if (fixedCells[row][column]) {
            return false;
        }
        return !isValueInRow(row, column, value)
                && !isValueInColumn(row, column, value)
                && !isValueInBox(row, column, value);
    }

    /**
     * Returns {@code true} when both row and column are within the valid 0–8 range.
     *
     * @param row    zero-based row index
     * @param column zero-based column index
     * @return {@code true} if coordinates are in bounds
     */
    private boolean isInBounds(final int row, final int column) {
        return row >= 0 && row < SIZE && column >= 0 && column < SIZE;
    }

    /**
     * Returns {@code true} if {@code value} already appears in {@code row},
     * excluding the target cell itself.
     *
     * @param row    zero-based row index
     * @param column zero-based column of the target cell (excluded from check)
     * @param value  digit to look for
     * @return {@code true} if the row already contains the value
     */
    private boolean isValueInRow(final int row, final int column, final int value) {
        for (int col = 0; col < SIZE; col++) {
            if (col != column && board[row][col] == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns {@code true} if {@code value} already appears in {@code column},
     * excluding the target cell itself.
     *
     * @param row    zero-based row of the target cell (excluded from check)
     * @param column zero-based column index
     * @param value  digit to look for
     * @return {@code true} if the column already contains the value
     */
    private boolean isValueInColumn(final int row, final int column, final int value) {
        for (int r = 0; r < SIZE; r++) {
            if (r != row && board[r][column] == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns {@code true} if {@code value} already appears in the 3×3 box
     * that contains ({@code row}, {@code column}), excluding the target cell.
     *
     * @param row    zero-based row index
     * @param column zero-based column index
     * @param value  digit to look for
     * @return {@code true} if the 3×3 box already contains the value
     */
    private boolean isValueInBox(final int row, final int column, final int value) {
        final int boxStartRow = (row / 3) * 3;
        final int boxStartCol = (column / 3) * 3;
        for (int r = boxStartRow; r < boxStartRow + 3; r++) {
            for (int col = boxStartCol; col < boxStartCol + 3; col++) {
                if ((r != row || col != column) && board[r][col] == value) {
                    return true;
                }
            }
        }
        return false;
    }
}
