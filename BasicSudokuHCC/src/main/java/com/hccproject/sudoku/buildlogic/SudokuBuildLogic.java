package com.hccproject.sudoku.buildlogic;

import com.hccproject.sudoku.problemdomain.IStorage;
import com.hccproject.sudoku.problemdomain.SudokuGame;
import com.hccproject.sudoku.problemdomain.persistence.LocalStorageImpl;
import com.hccproject.sudoku.userinterface.IUserInterfaceContract;
import com.hccproject.sudoku.computationlogic.GameLogic;
import com.hccproject.sudoku.userinterface.logic.ControlLogic;

import java.io.IOException;

public class SudokuBuildLogic {

    /**
     * Binds objects together to help them function and catch errors
     */
    public static void build(IUserInterfaceContract.View userInterface) throws IOException {
        SudokuGame initialState;
        IStorage storage = new LocalStorageImpl();

        try {


            initialState = storage.getGameData();
        } catch (IOException e) {

            initialState = GameLogic.getNewGame();

            storage.updateGameData(initialState);
        }

        IUserInterfaceContract.EventListener uiLogic = new ControlLogic(storage, userInterface);
        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialState);
    }
}
