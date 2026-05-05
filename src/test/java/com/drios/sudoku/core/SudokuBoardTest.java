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
}
