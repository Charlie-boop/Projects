package com.hccproject.sudoku.userinterface;

import com.hccproject.sudoku.problemdomain.SudokuGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;

/**
 *Interfaces for legibility and updates
 */
public interface IUserInterfaceContract {


    interface EventListener {
        void onSudokuInput(int x, int y, int input);
        void onDialogClick();
        void onResetClick();
        void onSolveClick();
        void onQuitClick();

    }



    interface View {
        void setListener(IUserInterfaceContract.EventListener listener);

        void updateSquare(int x, int y, int input);

        void updateBoard(SudokuGame game);
        void showDialog(String message);
        void showError(String message);
        void updateStreak(Integer integer);
        void updateBestTime(Duration duration);
    }
}
