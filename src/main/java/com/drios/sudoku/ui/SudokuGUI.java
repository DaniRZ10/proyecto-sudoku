package com.drios.sudoku.ui;

import com.drios.sudoku.core.Difficulty;
import com.drios.sudoku.core.SudokuBoard;
import com.drios.sudoku.core.SudokuGenerator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * Graphical User Interface for the Sudoku game using Swing.
 *
 * <p>Provides a 9x9 grid of text fields for playing Sudoku,
 * along with controls for starting a new game and selecting difficulty.</p>
 */
public class SudokuGUI {

    private static final Color COLOR_INVALID = new Color(255, 200, 200);
    private static final Color COLOR_FIXED = new Color(225, 225, 225);

    private final JFrame frame;
    private final JTextField[][] cells = new JTextField[SudokuBoard.SIZE][SudokuBoard.SIZE];
    private final JComboBox<Difficulty> difficultyCombo;
    private final JButton newGameButton;
    private final SudokuGenerator generator;
    private SudokuBoard board;
    private boolean isRendering = false;

    /**
     * Initializes the GUI components but does not show the window yet.
     */
    public SudokuGUI() {
        this.generator = new SudokuGenerator();
        frame = new JFrame("Sudoku Master");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // Control Panel
        final JPanel controlPanel = new JPanel();
        difficultyCombo = new JComboBox<>(Difficulty.values());
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> startNewGame());
        
        controlPanel.add(new JLabel("Difficulty:"));
        controlPanel.add(difficultyCombo);
        controlPanel.add(newGameButton);
        frame.add(controlPanel, BorderLayout.NORTH);

        // Board Panel
        final JPanel boardPanel = new JPanel(new GridLayout(SudokuBoard.SIZE, SudokuBoard.SIZE));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        for (int r = 0; r < SudokuBoard.SIZE; r++) {
            final int row = r;
            for (int c = 0; c < SudokuBoard.SIZE; c++) {
                final int col = c;
                final JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(new Font("SansSerif", Font.BOLD, 20));
                cell.setPreferredSize(new Dimension(50, 50));
                
                // Visual distinction for 3x3 blocks
                int top = (row % 3 == 0) ? 2 : 1;
                int left = (col % 3 == 0) ? 2 : 1;
                int bottom = (row == 8) ? 2 : 1;
                int right = (col == 8) ? 2 : 1;
                cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
                
                cell.getDocument().addDocumentListener(new DocumentListener() {
                    @Override public void insertUpdate(DocumentEvent e) { handleUpdate(); }
                    @Override public void removeUpdate(DocumentEvent e) { handleUpdate(); }
                    @Override public void changedUpdate(DocumentEvent e) { handleUpdate(); }
                    
                    private void handleUpdate() {
                        if (!isRendering) {
                            validateCell(row, col);
                        }
                    }
                });

                cells[row][col] = cell;
                boardPanel.add(cell);
            }
        }
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    /**
     * Validates the input in a specific cell and updates the board state.
     */
    private void validateCell(final int r, final int c) {
        final JTextField cell = cells[r][c];
        final String text = cell.getText().trim();

        if (text.isEmpty()) {
            board.placeNumber(r, c, SudokuBoard.EMPTY_CELL);
            cell.setBackground(Color.WHITE);
            return;
        }

        try {
            final int value = Integer.parseInt(text);
            if (value >= 1 && value <= 9 && board.isMovementValid(r, c, value)) {
                board.placeNumber(r, c, value);
                cell.setBackground(Color.WHITE);
                
                if (board.isSolved()) {
                    JOptionPane.showMessageDialog(frame, "Congratulations! You solved the Sudoku!");
                }
            } else {
                cell.setBackground(COLOR_INVALID);
            }
        } catch (NumberFormatException e) {
            cell.setBackground(COLOR_INVALID);
        }
    }

    /**
     * Generates a new Sudoku board and updates the grid.
     */
    private void startNewGame() {
        final Difficulty difficulty = (Difficulty) difficultyCombo.getSelectedItem();
        this.board = generator.generateBoard(difficulty);
        renderBoard();
    }

    /**
     * Updates the text and background color of all cells to match the current board state.
     */
    private void renderBoard() {
        isRendering = true;
        try {
            for (int r = 0; r < SudokuBoard.SIZE; r++) {
                for (int c = 0; c < SudokuBoard.SIZE; c++) {
                    final int value = board.getValue(r, c);
                    final boolean isFixed = board.isCellFixed(r, c);
                    final JTextField cell = cells[r][c];

                    if (value == SudokuBoard.EMPTY_CELL) {
                        cell.setText("");
                        cell.setEditable(true);
                        cell.setBackground(Color.WHITE);
                    } else {
                        cell.setText(String.valueOf(value));
                        cell.setEditable(!isFixed);
                        cell.setBackground(isFixed ? COLOR_FIXED : Color.WHITE);
                    }
                }
            }
        } finally {
            isRendering = false;
        }
    }

    /**
     * Makes the GUI window visible.
     */
    public void launch() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
}
