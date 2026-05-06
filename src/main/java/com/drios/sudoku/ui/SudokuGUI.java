package com.drios.sudoku.ui;

import com.drios.sudoku.core.Difficulty;
import com.drios.sudoku.core.SudokuBoard;

import javax.swing.*;
import java.awt.*;

/**
 * Graphical User Interface for the Sudoku game using Swing.
 *
 * <p>Provides a 9x9 grid of text fields for playing Sudoku,
 * along with controls for starting a new game and selecting difficulty.</p>
 */
public class SudokuGUI {

    private final JFrame frame;
    private final JTextField[][] cells = new JTextField[SudokuBoard.SIZE][SudokuBoard.SIZE];
    private final JComboBox<Difficulty> difficultyCombo;
    private final JButton newGameButton;

    /**
     * Initializes the GUI components but does not show the window yet.
     */
    public SudokuGUI() {
        frame = new JFrame("Sudoku Master");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // Control Panel
        final JPanel controlPanel = new JPanel();
        difficultyCombo = new JComboBox<>(Difficulty.values());
        newGameButton = new JButton("New Game");
        controlPanel.add(new JLabel("Difficulty:"));
        controlPanel.add(difficultyCombo);
        controlPanel.add(newGameButton);
        frame.add(controlPanel, BorderLayout.NORTH);

        // Board Panel
        final JPanel boardPanel = new JPanel(new GridLayout(SudokuBoard.SIZE, SudokuBoard.SIZE));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        for (int r = 0; r < SudokuBoard.SIZE; r++) {
            for (int c = 0; c < SudokuBoard.SIZE; c++) {
                final JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(new Font("SansSerif", Font.BOLD, 20));
                cell.setPreferredSize(new Dimension(50, 50));
                
                // Visual distinction for 3x3 blocks
                int top = (r % 3 == 0) ? 2 : 1;
                int left = (c % 3 == 0) ? 2 : 1;
                int bottom = (r == 8) ? 2 : 1;
                int right = (c == 8) ? 2 : 1;
                cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
                
                cells[r][c] = cell;
                boardPanel.add(cell);
            }
        }
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    /**
     * Makes the GUI window visible.
     */
    public void launch() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
}
