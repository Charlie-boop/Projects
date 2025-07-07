package com.hccproject.sudoku.userinterface.logic;


import com.hccproject.sudoku.computationlogic.GameGenerator;
import com.hccproject.sudoku.constants.GameState;
import com.hccproject.sudoku.constants.Messages;
import com.hccproject.sudoku.problemdomain.IStorage;
import com.hccproject.sudoku.problemdomain.SudokuGame;
import com.hccproject.sudoku.userinterface.IUserInterfaceContract;
import com.hccproject.sudoku.computationlogic.GameLogic;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * Contains the user interface logic.
 */

public class ControlLogic implements IUserInterfaceContract.EventListener {

    private IStorage storage;
    private IUserInterfaceContract.View view;

    public ControlLogic(IStorage storage, IUserInterfaceContract.View view) {
        this.storage = storage;
        this.view = view;
    }


    @Override
    public void onSudokuInput(int x, int y, int input) {
        try {
            SudokuGame gameData = storage.getGameData();
            int[][] newGridState = gameData.getCopyOfGridState();
            newGridState[x][y] = input;

            gameData = new SudokuGame(
                    GameLogic.checkForCompletion(newGridState),
                    newGridState
            );

            storage.updateGameData(gameData);


            view.updateSquare(x, y, input);


            if (gameData.getGameState() == GameState.COMPLETE)
                view.showDialog(Messages.GAME_COMPLETE);
        } catch (IOException e) {
            e.printStackTrace();
            view.showError(Messages.ERROR);
        }
    }

    @Override
    public void onDialogClick() {
        try {
            storage.updateGameData(
                    GameLogic.getNewGame()
            );

            view.updateBoard(storage.getGameData());
        } catch (IOException e) {
            view.showError(Messages.ERROR);
        }
    }

    @Override
    public void onResetClick() {
        try {
            storage.updateGameData(
                    GameLogic.getNewGame()
            );

            view.setListener(this);
        } catch (IOException e) {
            view.showError(Messages.ERROR);
        }
    }

    @Override
    public void onSolveClick() {

    }

    @Override
    public void onQuitClick() {

    }


}

