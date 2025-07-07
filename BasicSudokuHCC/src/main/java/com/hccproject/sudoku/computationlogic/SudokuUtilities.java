package com.hccproject.sudoku.computationlogic;

import com.hccproject.sudoku.problemdomain.SudokuGame;

/**
Copy values of the arrays for testing
 */
public class SudokuUtilities {

    public static void copySudokuArrayValues(int[][] oldArray, int[][] newArray) {
        for (int xIndex = 0; xIndex < SudokuGame.GRID_BOUNDARY; xIndex++){
            for (int yIndex = 0; yIndex < SudokuGame.GRID_BOUNDARY; yIndex++ ){
                newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
            }
        }
    }


    public static int[][] copyToNewArray(int[][] oldArray) {
        int[][] newArray = new int[SudokuGame.GRID_BOUNDARY][SudokuGame.GRID_BOUNDARY];
         copySudokuArrayValues(oldArray,newArray);
        return newArray;
    }
}
