package com.drios.sudoku.core;

/**
 * Represents the difficulty levels available for a Sudoku game.
 *
 * <p>Each difficulty level defines how many cells are left empty when
 * the board is generated. A higher number of empty cells means a
 * harder puzzle.</p>
 *
 * <ul>
 *   <li>{@link #EASY}   — 36 empty cells</li>
 *   <li>{@link #MEDIUM} — 46 empty cells</li>
 *   <li>{@link #HARD}   — 54 empty cells</li>
 * </ul>
 */
public enum Difficulty {

    /** Easy difficulty: 36 cells are left empty. */
    EASY(36),

    /** Medium difficulty: 46 cells are left empty. */
    MEDIUM(46),

    /** Hard difficulty: 54 cells are left empty. */
    HARD(54);

    /** Number of cells to remove from a fully solved board. */
    private final int emptyCellsCount;

    /**
     * Constructs a {@code Difficulty} constant with the given empty-cell count.
     *
     * @param emptyCellsCount number of cells to leave empty on the generated board
     */
    Difficulty(final int emptyCellsCount) {
        this.emptyCellsCount = emptyCellsCount;
    }

    /**
     * Returns the number of cells that will be left empty for this difficulty level.
     *
     * @return number of empty cells (36 for EASY, 46 for MEDIUM, 54 for HARD)
     */
    public int getEmptyCellsCount() {
        return emptyCellsCount;
    }
}
