package com.drios.sudoku.core;

/**
 * Thrown when a player attempts to place a number that violates Sudoku rules
 * or targets a cell that is not allowed to be modified.
 *
 * <p>This is an unchecked exception so that game-loop code remains readable
 * without mandatory try-catch blocks at every call site.</p>
 */
public class InvalidMoveException extends RuntimeException {

    /**
     * Constructs an {@code InvalidMoveException} with the specified detail message.
     *
     * @param message human-readable explanation of why the move is invalid
     */
    public InvalidMoveException(final String message) {
        super(message);
    }

    /**
     * Constructs an {@code InvalidMoveException} with the specified detail message
     * and a root cause.
     *
     * @param message human-readable explanation of why the move is invalid
     * @param cause   the underlying exception that triggered this one
     */
    public InvalidMoveException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
