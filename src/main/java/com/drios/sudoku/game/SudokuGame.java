package com.drios.sudoku.game;

import com.drios.sudoku.core.Difficulty;
import com.drios.sudoku.core.SudokuBoard;
import com.drios.sudoku.core.SudokuGenerator;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Orchestrates a Sudoku game session in the command-line interface.
 *
 * <p>This class handles user input, difficulty selection, and the main
 * gameplay loop. It uses {@link Scanner} and {@link PrintStream} for I/O,
 * which are injected via the constructor to facilitate unit testing.</p>
 */
public class SudokuGame {

    private final Scanner in;
    private final PrintStream out;
    private final SudokuGenerator generator;
    private SudokuBoard board;

    /**
     * Creates a new game session with the given I/O streams.
     *
     * @param in  the scanner to read user input from
     * @param out the stream to print game output to
     */
    public SudokuGame(final Scanner in, final PrintStream out) {
        this.in = in;
        this.out = out;
        this.generator = new SudokuGenerator();
    }

    /**
     * Starts the game session.
     *
     * <p>Greets the user, prompts for difficulty, generates the board,
     * and enters the main interaction loop.</p>
     */
    public void start() {
        out.println("Welcome to Sudoku!");
        final Difficulty difficulty = selectDifficulty();
        
        out.println("Generating " + difficulty + " board...");
        this.board = generator.generateBoard(difficulty);
        
        play();
    }

    /**
     * Main gameplay loop.
     *
     * <p>Repeatedly prints the board and processes player commands
     * until the board is solved or the player quits.</p>
     */
    private void play() {
        while (true) {
            out.println("\nCurrent Board:");
            board.printBoard(out);

            if (board.isSolved()) {
                out.println("\nCongratulations! You solved the Sudoku!");
                break;
            }

            out.println("\nEnter move (row col value) or 'quit' to exit:");
            out.print("> ");

            final String input = in.next();
            if ("quit".equalsIgnoreCase(input)) {
                out.println("Game abandoned. See you next time!");
                break;
            }

            try {
                final int row = Integer.parseInt(input);
                final int col = in.nextInt();
                final int val = in.nextInt();

                board.placeNumber(row, col, val);
                
                if (board.isSolved()) {
                    out.println("\nCurrent Board:");
                    board.printBoard(out);
                    out.println("\nCongratulations! You solved the Sudoku!");
                    break;
                }
            } catch (NumberFormatException e) {
                out.println("Invalid input. Please enter 'row col value' (e.g., 0 0 5) or 'quit'.");
                in.nextLine(); // consume remaining line
            } catch (Exception e) {
                out.println("Error: " + e.getMessage());
                in.nextLine(); // consume remaining line
            }
        }
    }

    /**
     * Prompts the user to choose a difficulty level.
     *
     * @return the selected {@link Difficulty}
     */
    private Difficulty selectDifficulty() {
        while (true) {
            out.println("\nSelect Difficulty:");
            out.println("1. EASY");
            out.println("2. MEDIUM");
            out.println("3. HARD");
            out.print("Choice: ");

            if (in.hasNextInt()) {
                final int choice = in.nextInt();
                switch (choice) {
                    case 1: return Difficulty.EASY;
                    case 2: return Difficulty.MEDIUM;
                    case 3: return Difficulty.HARD;
                    default:
                        out.println("Invalid choice. Please enter a number between 1 and 3.");
                }
            } else {
                out.println("Invalid input. Please enter a number.");
                in.next(); // clear invalid token
            }
        }
    }

    /**
     * Returns the current board. Primarily for testing.
     *
     * @return the current {@link SudokuBoard} or {@code null} if not yet generated
     */
    public SudokuBoard getBoard() {
        return board;
    }
}
