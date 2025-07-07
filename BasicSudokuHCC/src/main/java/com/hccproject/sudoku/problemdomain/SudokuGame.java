package com.hccproject.sudoku.problemdomain;

import com.hccproject.sudoku.computationlogic.SudokuUtilities;
import com.hccproject.sudoku.constants.GameState;

import java.io.Serializable;


public class SudokuGame implements Serializable {
    private final GameState gameState;
    private final int[][] gridState;

    /**
     * Determines the size of the grid
     */
    public static final int GRID_BOUNDARY = 9;

    /**
    Controls several important game states
     */
    public SudokuGame(GameState gameState, int[][] gridState) {
        this.gameState = gameState;
        this.gridState = gridState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int[][] getCopyOfGridState() {
        return SudokuUtilities.copyToNewArray(gridState);
    }

}
