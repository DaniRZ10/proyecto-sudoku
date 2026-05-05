package com.drios.sudoku.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SudokuBoard} skeleton:
 * constructor initialization, getValue, setValue and isCellFixed.
 */
@DisplayName("SudokuBoard — skeleton tests")
class SudokuBoardTest {

    private SudokuBoard board;

    @BeforeEach
    void setUp() {
        board = new SudokuBoard();
    }

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("constructor_should_initializeAllCellsToZero")
    void constructor_should_initializeAllCellsToZero() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertEquals(0, board.getValue(row, col),
                        "Expected cell [" + row + "][" + col + "] to be 0 after construction");
            }
        }
    }

    @Test
    @DisplayName("constructor_should_markNoCellAsFixed")
    void constructor_should_markNoCellAsFixed() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertFalse(board.isCellFixed(row, col),
                        "Expected cell [" + row + "][" + col + "] to not be fixed after construction");
            }
        }
    }

    // -------------------------------------------------------------------------
    // getValue
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("getValue_should_returnStoredValue_when_cellHasBeenSet")
    void getValue_should_returnStoredValue_when_cellHasBeenSet() {
        board.setValue(3, 5, 7, false);
        assertEquals(7, board.getValue(3, 5));
    }

    @Test
    @DisplayName("getValue_should_returnZero_when_cellIsEmpty")
    void getValue_should_returnZero_when_cellIsEmpty() {
        assertEquals(0, board.getValue(0, 0));
    }

    // -------------------------------------------------------------------------
    // setValue
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("setValue_should_updateBoardValue")
    void setValue_should_updateBoardValue() {
        board.setValue(0, 0, 5, false);
        assertEquals(5, board.getValue(0, 0));
    }

    @Test
    @DisplayName("setValue_should_markCellAsFixed_when_fixedIsTrue")
    void setValue_should_markCellAsFixed_when_fixedIsTrue() {
        board.setValue(1, 1, 9, true);
        assertTrue(board.isCellFixed(1, 1));
    }

    @Test
    @DisplayName("setValue_should_notMarkCellAsFixed_when_fixedIsFalse")
    void setValue_should_notMarkCellAsFixed_when_fixedIsFalse() {
        board.setValue(2, 2, 4, false);
        assertFalse(board.isCellFixed(2, 2));
    }

    @Test
    @DisplayName("setValue_should_updateLastRow_and_lastColumn")
    void setValue_should_updateLastRow_and_lastColumn() {
        board.setValue(8, 8, 3, false);
        assertEquals(3, board.getValue(8, 8));
    }

    // -------------------------------------------------------------------------
    // isCellFixed
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("isCellFixed_should_returnTrue_when_cellWasSetAsFixed")
    void isCellFixed_should_returnTrue_when_cellWasSetAsFixed() {
        board.setValue(4, 4, 6, true);
        assertTrue(board.isCellFixed(4, 4));
    }

    @Test
    @DisplayName("isCellFixed_should_returnFalse_when_cellWasSetAsNotFixed")
    void isCellFixed_should_returnFalse_when_cellWasSetAsNotFixed() {
        board.setValue(4, 4, 6, false);
        assertFalse(board.isCellFixed(4, 4));
    }

    @Test
    @DisplayName("isCellFixed_should_returnFalse_when_cellWasNeverSet")
    void isCellFixed_should_returnFalse_when_cellWasNeverSet() {
        assertFalse(board.isCellFixed(7, 3));
    }

    // -------------------------------------------------------------------------
    // isMovementValid — T005
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("isMovementValid_should_returnTrue_when_moveIsLegal")
    void isMovementValid_should_returnTrue_when_moveIsLegal() {
        // Empty board: any value 1-9 in any cell is valid
        assertTrue(board.isMovementValid(0, 0, 5));
    }

    @Test
    @DisplayName("isMovementValid_should_returnFalse_when_valueAlreadyInSameRow")
    void isMovementValid_should_returnFalse_when_valueAlreadyInSameRow() {
        board.setValue(0, 3, 7, false);
        assertFalse(board.isMovementValid(0, 8, 7),
                "Value 7 already in row 0 at column 3");
    }

    @Test
    @DisplayName("isMovementValid_should_returnFalse_when_valueAlreadyInSameColumn")
    void isMovementValid_should_returnFalse_when_valueAlreadyInSameColumn() {
        board.setValue(2, 5, 4, false);
        assertFalse(board.isMovementValid(7, 5, 4),
                "Value 4 already in column 5 at row 2");
    }

    @Test
    @DisplayName("isMovementValid_should_returnFalse_when_valueAlreadyInSameBox")
    void isMovementValid_should_returnFalse_when_valueAlreadyInSameBox() {
        // top-left box: rows 0-2, cols 0-2
        board.setValue(1, 1, 9, false);
        assertFalse(board.isMovementValid(0, 2, 9),
                "Value 9 already in the top-left 3x3 box");
    }

    @Test
    @DisplayName("isMovementValid_should_returnFalse_when_valueIsZero")
    void isMovementValid_should_returnFalse_when_valueIsZero() {
        assertFalse(board.isMovementValid(0, 0, 0));
    }

    @Test
    @DisplayName("isMovementValid_should_returnFalse_when_valueIsGreaterThanNine")
    void isMovementValid_should_returnFalse_when_valueIsGreaterThanNine() {
        assertFalse(board.isMovementValid(0, 0, 10));
    }

    @Test
    @DisplayName("isMovementValid_should_returnFalse_when_valueIsNegative")
    void isMovementValid_should_returnFalse_when_valueIsNegative() {
        assertFalse(board.isMovementValid(0, 0, -1));
    }

    @Test
    @DisplayName("isMovementValid_should_returnFalse_when_cellIsFixed")
    void isMovementValid_should_returnFalse_when_cellIsFixed() {
        board.setValue(3, 3, 6, true);
        assertFalse(board.isMovementValid(3, 3, 1),
                "Cell [3][3] is fixed and must not be modified");
    }

    @Test
    @DisplayName("isMovementValid_should_returnFalse_when_rowIsOutOfRange")
    void isMovementValid_should_returnFalse_when_rowIsOutOfRange() {
        assertFalse(board.isMovementValid(9, 0, 5));
        assertFalse(board.isMovementValid(-1, 0, 5));
    }

    @Test
    @DisplayName("isMovementValid_should_returnFalse_when_columnIsOutOfRange")
    void isMovementValid_should_returnFalse_when_columnIsOutOfRange() {
        assertFalse(board.isMovementValid(0, 9, 5));
        assertFalse(board.isMovementValid(0, -1, 5));
    }

    @Test
    @DisplayName("isMovementValid_should_returnTrue_when_sameValuePlacedOnItsOwnCell")
    void isMovementValid_should_returnTrue_when_sameValuePlacedOnItsOwnCell() {
        // Placing a value on the cell that already holds it (re-confirming)
        // should not conflict with itself in row/col/box checks
        board.setValue(4, 4, 3, false);
        assertTrue(board.isMovementValid(4, 4, 3),
                "Placing the same value on its own non-fixed cell should be valid");
    }

    // -------------------------------------------------------------------------
    // placeNumber — T006
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("placeNumber_should_updateBoard_when_moveIsLegal")
    void placeNumber_should_updateBoard_when_moveIsLegal() {
        board.placeNumber(0, 0, 5);
        assertEquals(5, board.getValue(0, 0));
    }

    @Test
    @DisplayName("placeNumber_should_notMarkCellAsFixed_when_playerPlacesValue")
    void placeNumber_should_notMarkCellAsFixed_when_playerPlacesValue() {
        board.placeNumber(1, 2, 7);
        assertFalse(board.isCellFixed(1, 2),
                "Player-placed values must never become fixed");
    }

    @Test
    @DisplayName("placeNumber_should_throwInvalidMoveException_when_rowConflictExists")
    void placeNumber_should_throwInvalidMoveException_when_rowConflictExists() {
        board.setValue(0, 0, 3, false);
        assertThrows(InvalidMoveException.class,
                () -> board.placeNumber(0, 5, 3),
                "Expected InvalidMoveException for row conflict");
    }

    @Test
    @DisplayName("placeNumber_should_throwInvalidMoveException_when_columnConflictExists")
    void placeNumber_should_throwInvalidMoveException_when_columnConflictExists() {
        board.setValue(0, 4, 8, false);
        assertThrows(InvalidMoveException.class,
                () -> board.placeNumber(6, 4, 8),
                "Expected InvalidMoveException for column conflict");
    }

    @Test
    @DisplayName("placeNumber_should_throwInvalidMoveException_when_boxConflictExists")
    void placeNumber_should_throwInvalidMoveException_when_boxConflictExists() {
        board.setValue(6, 6, 2, false);
        assertThrows(InvalidMoveException.class,
                () -> board.placeNumber(7, 7, 2),
                "Expected InvalidMoveException for 3x3 box conflict");
    }

    @Test
    @DisplayName("placeNumber_should_throwInvalidMoveException_when_cellIsFixed")
    void placeNumber_should_throwInvalidMoveException_when_cellIsFixed() {
        board.setValue(3, 3, 6, true);
        assertThrows(InvalidMoveException.class,
                () -> board.placeNumber(3, 3, 1),
                "Expected InvalidMoveException when targeting a fixed cell");
    }

    @Test
    @DisplayName("placeNumber_should_throwInvalidMoveException_when_valueIsOutOfRange")
    void placeNumber_should_throwInvalidMoveException_when_valueIsOutOfRange() {
        assertThrows(InvalidMoveException.class,
                () -> board.placeNumber(0, 0, 0));
        assertThrows(InvalidMoveException.class,
                () -> board.placeNumber(0, 0, 10));
    }

    @Test
    @DisplayName("placeNumber_should_throwInvalidMoveException_when_coordinatesAreOutOfRange")
    void placeNumber_should_throwInvalidMoveException_when_coordinatesAreOutOfRange() {
        assertThrows(InvalidMoveException.class,
                () -> board.placeNumber(-1, 0, 5));
        assertThrows(InvalidMoveException.class,
                () -> board.placeNumber(0, 9, 5));
    }

    @Test
    @DisplayName("placeNumber_should_overwritePreviousValue_when_moveIsLegal")
    void placeNumber_should_overwritePreviousValue_when_moveIsLegal() {
        board.placeNumber(5, 5, 4);
        board.placeNumber(5, 5, 4); // same value on same non-fixed cell is valid
        assertEquals(4, board.getValue(5, 5));
    }

    // -------------------------------------------------------------------------
    // isSolved — T007
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("isSolved_should_returnFalse_when_boardIsEmpty")
    void isSolved_should_returnFalse_when_boardIsEmpty() {
        assertFalse(board.isSolved());
    }

    @Test
    @DisplayName("isSolved_should_returnFalse_when_boardIsIncomplete")
    void isSolved_should_returnFalse_when_boardIsIncomplete() {
        fillValidBoard(board);
        board.setValue(8, 8, 0, false); // Make one cell empty
        assertFalse(board.isSolved());
    }

    @Test
    @DisplayName("isSolved_should_returnTrue_when_boardIsFullAndValid")
    void isSolved_should_returnTrue_when_boardIsFullAndValid() {
        fillValidBoard(board);
        assertTrue(board.isSolved());
    }

    @Test
    @DisplayName("isSolved_should_returnFalse_when_boardIsFullButHasConflicts")
    void isSolved_should_returnFalse_when_boardIsFullButHasConflicts() {
        fillValidBoard(board);
        // Introduce a conflict: duplicate value in a row
        // (Changing [0][0] from 5 to 3 creates a conflict with [0][1])
        board.setValue(0, 0, 3, false); 
        assertFalse(board.isSolved());
    }

    /**
     * Helper to fill a board with a known valid solution.
     */
    private void fillValidBoard(SudokuBoard board) {
        int[][] solution = {
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
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                board.setValue(r, c, solution[r][c], false);
            }
        }
    }
}

