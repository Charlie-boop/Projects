package com.hccproject.sudoku.problemdomain;

import java.io.IOException;

/**
To keep frontend and backend separate
 */
public interface IStorage {
    void updateGameData(SudokuGame game) throws IOException;
    SudokuGame getGameData() throws IOException;
}
