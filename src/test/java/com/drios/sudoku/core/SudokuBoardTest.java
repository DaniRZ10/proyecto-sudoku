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
}
