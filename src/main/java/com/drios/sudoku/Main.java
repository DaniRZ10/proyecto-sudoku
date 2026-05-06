package com.drios.sudoku;

import com.drios.sudoku.game.SudokuGame;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Entry point for the Sudoku application.
 *
 * <p>Supports both CLI and GUI modes. Use the {@code --gui} flag to
 * launch the graphical interface (when implemented).</p>
 */
public class Main {

    /**
     * Main entry point.
     *
     * @param args command-line arguments
     */
    public static void main(final String[] args) {
        boolean useGui = false;
        for (final String arg : args) {
            if ("--gui".equalsIgnoreCase(arg)) {
                useGui = true;
                break;
            }
        }

        if (useGui) {
            launchGui();
        } else {
            launchCli();
        }
    }

    /**
     * Launches the CLI game.
     */
    private static void launchCli() {
        final Scanner scanner = new Scanner(System.in);
        final PrintStream out = System.out;
        final SudokuGame game = new SudokuGame(scanner, out);
        game.start();
    }

    /**
     * Launches the GUI game.
     */
    private static void launchGui() {
        // SudokuGUI implementation will be added in Phase 4 (T022)
        System.out.println("GUI mode requested, but it's not implemented yet.");
        System.out.println("Launching CLI mode instead...");
        launchCli();
    }
}
