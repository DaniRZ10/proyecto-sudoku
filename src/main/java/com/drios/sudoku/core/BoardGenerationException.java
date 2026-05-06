package com.drios.sudoku.core;

/**
 * Thrown when the {@code SudokuGenerator} fails to produce a valid board.
 *
 * <p>This can happen if the backtracking algorithm exhausts all possibilities
 * without finding a solution, which should be extremely rare for a standard
 * 9×9 Sudoku but is surfaced explicitly for robustness.</p>
 */
public class BoardGenerationException extends RuntimeException {

    /**
     * Constructs a {@code BoardGenerationException} with the specified detail message.
     *
     * @param message human-readable explanation of why the board could not be generated
     */
    public BoardGenerationException(final String message) {
        super(message);
    }

    /**
     * Constructs a {@code BoardGenerationException} with the specified detail message
     * and a root cause.
     *
     * @param message human-readable explanation of why the board could not be generated
     * @param cause   the underlying exception that triggered this one
     */
    public BoardGenerationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
