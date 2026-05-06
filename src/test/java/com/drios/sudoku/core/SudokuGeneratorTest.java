package com.drios.sudoku.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SudokuGenerator}.
 *
 * <p>T011 covers the backtracking {@code solve(int[][])} helper that is used
 * internally during board generation.</p>
 *
 * <p>Because {@code solve} is package-private (not {@code private}), these
 * tests can call it directly without reflection, keeping the test readable
 * and the production code clean.</p>
 *
 * <p><b>Contract of {@code solve()}:</b> it is a board <em>completer</em>,
 * not a validator. It only invokes constraint-checking when it tries to place
 * a new digit in an empty cell. Boards with pre-existing conflicts among
 * non-zero cells are outside the intended input domain; tests that need to
 * exercise the {@code false} return path must use boards where a genuinely
 * empty cell has no valid digit, not boards whose pre-filled cells already
 * violate Sudoku rules.</p>
 */
class SudokuGeneratorTest {

    private SudokuGenerator generator;

    // Well-known valid, fully solved 9×9 board (Wikipedia example)
    private static final int[][] CLASSIC_SOLVED = {
        {5, 3, 4, 6, 7, 8, 9, 1, 2},
        {6, 7, 2, 1, 9, 5, 3, 4, 8},
        {1, 9, 8, 3, 4, 2, 5, 6, 7},
        {8, 5, 9, 7, 6, 1, 4, 2, 3},
        {4, 2, 6, 8, 5, 3, 7, 9, 1},
        {7, 1, 3, 9, 2, 4, 8, 5, 6},
        {9, 6, 1, 5, 3, 7, 2, 8, 4},
        {2, 8, 7, 4, 1, 9, 6, 3, 5},
        {3, 4, 5, 2, 8, 6, 1, 7, 9}
    };

    /** Deep-copies a 2-D int array so each test gets a fresh instance. */
    private static int[][] copy(final int[][] src) {
        final int[][] dst = new int[src.length][];
        for (int i = 0; i < src.length; i++) {
            dst[i] = src[i].clone();
        }
        return dst;
    }

    @BeforeEach
    void setUp() {
        generator = new SudokuGenerator();
    }

    // -------------------------------------------------------------------------
    // T011 — solve(int[][] board)
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("solve_should_returnTrue_when_boardIsAlreadyComplete")
    void solve_should_returnTrue_when_boardIsAlreadyComplete() {
        assertTrue(generator.solve(copy(CLASSIC_SOLVED)),
                "A fully filled valid board should return true without changes");
    }

    @Test
    @DisplayName("solve_should_returnTrue_and_fillBoard_when_partiallyFilled")
    void solve_should_returnTrue_and_fillBoard_when_partiallyFilled() {
        // Classic puzzle with ~51 empty cells — solvable and has a unique solution
        int[][] partial = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        assertTrue(generator.solve(partial), "A solvable partial board should return true");

        // All cells must be filled
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                assertNotEquals(0, partial[r][c],
                        "Cell [" + r + "][" + c + "] must not be 0 after solve");
            }
        }

        // The completed board must satisfy isSolved()
        final SudokuBoard board = new SudokuBoard();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                board.setValue(r, c, partial[r][c], true);
            }
        }
        assertTrue(board.isSolved(), "Board produced by solve() must satisfy isSolved()");
    }

    @Test
    @DisplayName("solve_should_returnTrue_when_boardHasOnlyOneEmptyCell")
    void solve_should_returnTrue_when_boardHasOnlyOneEmptyCell() {
        // Clear the last cell of the classic solved board
        final int[][] nearlyComplete = copy(CLASSIC_SOLVED);
        nearlyComplete[8][8] = 0;

        assertTrue(generator.solve(nearlyComplete),
                "Board with a single empty cell should be solvable");
        assertEquals(9, nearlyComplete[8][8],
                "The only empty cell must be filled with the unique valid digit (9)");
    }

    @Test
    @DisplayName("solve_should_returnFalse_when_noValidDigitFitsEmptyCell")
    void solve_should_returnFalse_when_noValidDigitFitsEmptyCell() {
        // Board with exactly one empty cell at [8][8].
        // Row 8 contains 1-8 (only 9 is row-valid for [8][8]).
        // Column 8 already contains 9 (at row 0) → 9 is col-blocked.
        // Therefore no digit 1-9 passes isSafe for [8][8] → solve must return false.
        //
        // Rows 0-7 and cols 0-7 may have constraint violations among themselves
        // (they are not a valid Sudoku overall), but that is irrelevant: solve()
        // only calls isSafe when placing a NEW digit into an EMPTY cell, so the
        // pre-filled cells are treated as immutable context.
        final int[][] blocked = {
            // col 8 = 9 at row 0 → blocks the only row-valid candidate for [8][8]
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {2, 3, 4, 5, 6, 7, 8, 9, 1},
            {3, 4, 5, 6, 7, 8, 9, 1, 2},
            {4, 5, 6, 7, 8, 9, 1, 2, 3},
            {5, 6, 7, 8, 9, 1, 2, 3, 4},
            {6, 7, 8, 9, 1, 2, 3, 4, 5},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},
            {8, 9, 1, 2, 3, 4, 5, 6, 7},
            {1, 2, 3, 4, 5, 6, 7, 8, 0}  // row 8: 1-8 present → only 9 row-valid
                                           // col 8 has 9 at row 0 → 9 col-blocked → FALSE
        };

        assertFalse(generator.solve(blocked),
                "No digit fits [8][8]: 1-8 blocked by row 8, 9 blocked by col 8");
    }

    // -------------------------------------------------------------------------
    // T012 — generateFullBoard()
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("generateFullBoard_should_produceValidSolvedGrid")
    void generateFullBoard_should_produceValidSolvedGrid() {
        final int[][] grid = generator.generateFullBoard();

        // Verify dimensions
        assertEquals(9, grid.length);
        assertEquals(9, grid[0].length);

        // Verify it passes SudokuBoard.isSolved()
        final SudokuBoard board = new SudokuBoard();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                board.setValue(r, c, grid[r][c], true);
            }
        }
        assertTrue(board.isSolved(), "The generated full board must be a valid solution");
    }

    @Test
    @DisplayName("generateFullBoard_should_produceDifferentBoards_when_calledTwice")
    void generateFullBoard_should_produceDifferentBoards_when_calledTwice() {
        final int[][] grid1 = generator.generateFullBoard();
        final int[][] grid2 = generator.generateFullBoard();

        assertFalse(Arrays.deepEquals(grid1, grid2),
                "Two consecutive generations should produce different boards (randomization)");
    }

    // -------------------------------------------------------------------------
    // T013 — generateBoard(Difficulty) cell carving
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("generateBoard_should_haveExactly36EmptyCells_when_Easy")
    void generateBoard_should_haveExactly36EmptyCells_when_Easy() {
        final SudokuBoard board = generator.generateBoard(Difficulty.EASY);
        assertEquals(36, countEmptyCells(board), "EASY difficulty must have 36 empty cells");
    }

    @Test
    @DisplayName("generateBoard_should_haveExactly46EmptyCells_when_Medium")
    void generateBoard_should_haveExactly46EmptyCells_when_Medium() {
        final SudokuBoard board = generator.generateBoard(Difficulty.MEDIUM);
        assertEquals(46, countEmptyCells(board), "MEDIUM difficulty must have 46 empty cells");
    }

    @Test
    @DisplayName("generateBoard_should_haveExactly54EmptyCells_when_Hard")
    void generateBoard_should_haveExactly54EmptyCells_when_Hard() {
        final SudokuBoard board = generator.generateBoard(Difficulty.HARD);
        assertEquals(54, countEmptyCells(board), "HARD difficulty must have 54 empty cells");
    }

    /** Helper to count cells with value 0 in a SudokuBoard. */
    private int countEmptyCells(final SudokuBoard board) {
        int empty = 0;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board.getValue(r, c) == SudokuBoard.EMPTY_CELL) {
                    empty++;
                }
            }
        }
        return empty;
    }
}
