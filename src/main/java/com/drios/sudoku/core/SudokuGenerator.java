package com.drios.sudoku.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generates valid Sudoku puzzles of varying difficulty.
 *
 * <p>The generation process follows two steps:</p>
 * <ol>
 *   <li>Build a fully solved board using randomised backtracking
 *       ({@link #generateFullBoard()}).</li>
 *   <li>Remove a controlled number of cells according to the chosen
 *       {@link Difficulty}, marking the remaining cells as fixed
 *       ({@link #generateBoard(Difficulty)}).</li>
 * </ol>
 *
 * <p>The {@link #solve(int[][])} helper is package-private so that unit
 * tests in the same package can exercise it directly without reflection.</p>
 */
public class SudokuGenerator {

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    /**
     * Generates a playable {@link SudokuBoard} with the specified difficulty.
     *
     * <p>The returned board has exactly {@link Difficulty#getEmptyCellsCount()}
     * empty cells (value {@code 0}); all other cells are filled and marked as
     * fixed so that the player cannot modify them.</p>
     *
     * @param difficulty the desired puzzle difficulty (must not be {@code null})
     * @return a new {@link SudokuBoard} ready to play
     * @throws BoardGenerationException if backtracking fails to produce a valid
     *                                  solution (should never happen in practice)
     */
    public SudokuBoard generateBoard(final Difficulty difficulty) {
        final int[][] fullGrid = generateFullBoard();
        carveCells(fullGrid, difficulty.getEmptyCellsCount());

        final SudokuBoard board = new SudokuBoard();
        for (int r = 0; r < SudokuBoard.SIZE; r++) {
            for (int c = 0; c < SudokuBoard.SIZE; c++) {
                final int value = fullGrid[r][c];
                board.setValue(r, c, value, value != SudokuBoard.EMPTY_CELL);
            }
        }
        return board;
    }

    // -------------------------------------------------------------------------
    // Internal helpers (package-private for testability)
    // -------------------------------------------------------------------------

    /**
     * Builds a fully solved, randomised 9×9 Sudoku grid.
     *
     * <p>Starts with an all-zero grid and fills it using
     * {@link #solve(int[][])} with digits shuffled randomly per cell so that
     * each call yields a different board.</p>
     *
     * @return a 9×9 array where every cell holds a value 1–9 and all Sudoku
     *         constraints are satisfied
     * @throws BoardGenerationException if no solution can be found (should be
     *                                  unreachable given a blank starting grid)
     */
    int[][] generateFullBoard() {
        final int[][] grid = new int[SudokuBoard.SIZE][SudokuBoard.SIZE];
        if (!solve(grid)) {
            throw new BoardGenerationException("Backtracking failed to generate a full board");
        }
        return grid;
    }

    /**
     * Fills empty cells in {@code board} using recursive backtracking.
     *
     * <p>Scans left-to-right, top-to-bottom for the first empty cell
     * ({@code 0}) and tries digits 1–9 in order. A digit is accepted when it
     * does not conflict with any value already in the same row, column, or
     * 3×3 box. If no digit fits, the method backtracks.</p>
     *
     * <p>This method is package-private (not {@code private}) so that
     * {@link SudokuGeneratorTest} can call it directly.</p>
     *
     * @param board a 9×9 int array; cells with value {@code 0} are treated as
     *              empty and will be filled; cells with non-zero values are
     *              treated as pre-placed and are never modified
     * @return {@code true} if the board was successfully filled;
     *         {@code false} if the current state has no valid completion
     *         (e.g. because it already contains a constraint violation)
     */
    boolean solve(final int[][] board) {
        for (int row = 0; row < SudokuBoard.SIZE; row++) {
            for (int col = 0; col < SudokuBoard.SIZE; col++) {
                if (board[row][col] == SudokuBoard.EMPTY_CELL) {
                    final List<Integer> digits = new ArrayList<>(SudokuBoard.SIZE);
                    for (int i = 1; i <= SudokuBoard.SIZE; i++) {
                        digits.add(i);
                    }
                    Collections.shuffle(digits);

                    for (int digit : digits) {
                        if (isSafe(board, row, col, digit)) {
                            board[row][col] = digit;
                            if (solve(board)) {
                                return true;
                            }
                            board[row][col] = SudokuBoard.EMPTY_CELL; // backtrack
                        }
                    }
                    return false; // no digit fits — trigger backtrack
                }
            }
        }
        return true; // no empty cell found — board is complete
    }

    /**
     * Removes exactly {@code emptyCount} cells from a fully solved grid.
     *
     * <p>Cells are chosen uniformly at random. The same cell is never removed
     * twice.</p>
     *
     * @param board      a fully solved 9×9 grid (modified in place)
     * @param emptyCount the number of cells to clear (set to {@code 0})
     */
    private void carveCells(final int[][] board, final int emptyCount) {
        int removed = 0;
        while (removed < emptyCount) {
            final int row = (int) (Math.random() * SudokuBoard.SIZE);
            final int col = (int) (Math.random() * SudokuBoard.SIZE);
            if (board[row][col] != SudokuBoard.EMPTY_CELL) {
                board[row][col] = SudokuBoard.EMPTY_CELL;
                removed++;
            }
        }
    }

    // -------------------------------------------------------------------------
    // Constraint-checking helpers
    // -------------------------------------------------------------------------

    /**
     * Returns {@code true} when placing {@code digit} at ({@code row}, {@code col})
     * does not violate any Sudoku constraint (row, column, or 3×3 box).
     *
     * @param board the current grid state
     * @param row   zero-based row index
     * @param col   zero-based column index
     * @param digit the candidate value (1–9)
     * @return {@code true} if the placement is constraint-safe
     */
    private boolean isSafe(final int[][] board, final int row, final int col, final int digit) {
        // Check row
        for (int c = 0; c < SudokuBoard.SIZE; c++) {
            if (board[row][c] == digit) {
                return false;
            }
        }
        // Check column
        for (int r = 0; r < SudokuBoard.SIZE; r++) {
            if (board[r][col] == digit) {
                return false;
            }
        }
        // Check 3×3 box
        final int boxStartRow = (row / 3) * 3;
        final int boxStartCol = (col / 3) * 3;
        for (int r = boxStartRow; r < boxStartRow + 3; r++) {
            for (int c = boxStartCol; c < boxStartCol + 3; c++) {
                if (board[r][c] == digit) {
                    return false;
                }
            }
        }
        return true;
    }
}
