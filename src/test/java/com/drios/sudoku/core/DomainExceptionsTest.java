package com.drios.sudoku.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for domain exceptions to ensure constructors correctly initialize messages and causes.
 */
class DomainExceptionsTest {

  /** Verifies that InvalidMoveException correctly stores the message when constructed with only a message. */
  @Test
  void invalidMoveException_should_storeMessage_when_calledWithMessageOnly() {
    final String expectedMessage = "Test message for InvalidMoveException";

    final InvalidMoveException exception = new InvalidMoveException(expectedMessage);

    assertEquals(expectedMessage, exception.getMessage());
  }

  /** Verifies that InvalidMoveException correctly stores both message and cause. */
  @Test
  void invalidMoveException_should_storeMessageAndCause_when_calledWithBoth() {
    final String expectedMessage = "Test message with cause";
    final Throwable expectedCause = new RuntimeException("Underlying cause");

    final InvalidMoveException exception = new InvalidMoveException(expectedMessage, expectedCause);

    assertEquals(expectedMessage, exception.getMessage());
    assertEquals(expectedCause, exception.getCause());
  }

  /** Verifies that BoardGenerationException correctly stores the message when constructed with only a message. */
  @Test
  void boardGenerationException_should_storeMessage_when_calledWithMessageOnly() {
    final String expectedMessage = "Test message for BoardGenerationException";

    final BoardGenerationException exception = new BoardGenerationException(expectedMessage);

    assertEquals(expectedMessage, exception.getMessage());
  }

  /** Verifies that BoardGenerationException correctly stores both message and cause. */
  @Test
  void boardGenerationException_should_storeMessageAndCause_when_calledWithBoth() {
    final String expectedMessage = "Test message with cause";
    final Throwable expectedCause = new IllegalArgumentException("Bad argument");

    final BoardGenerationException exception = new BoardGenerationException(expectedMessage, expectedCause);

    assertEquals(expectedMessage, exception.getMessage());
    assertEquals(expectedCause, exception.getCause());
  }
}
