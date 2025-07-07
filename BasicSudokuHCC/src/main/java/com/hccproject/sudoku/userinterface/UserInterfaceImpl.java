package com.hccproject.sudoku.userinterface;

import com.hccproject.sudoku.constants.GameState;
import com.hccproject.sudoku.problemdomain.Coordinates;
import com.hccproject.sudoku.problemdomain.SudokuGame;
import com.hccproject.sudoku.computationlogic.GameGenerator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.HashMap;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

/**
 * Manages the game window
 */
public class UserInterfaceImpl implements IUserInterfaceContract.View,
        EventHandler<KeyEvent>, ActionListener {
    private final Stage stage;
    private final Group root;

    private HashMap<Coordinates, SudokuTextField> textFieldCoordinates;

    private IUserInterfaceContract.EventListener listener;


    private static final double WINDOW_Y = 732;
    private static final double WINDOW_X = 668;

    private static final double BOARD_PADDING = 50;

    private static final double BOARD_X_AND_Y = 576;
    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0, 100, 200);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(224, 242, 241);
    private static final String SUDOKU = "Basic Sudoku Game";
    private static final String StreakText = "Best Streak";
    private static final String TimerText = "Fastest Completion Time";
    private static SudokuGame game;


    /**
     *  A hashmap for compact referencing
     */
    public UserInterfaceImpl(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface();
    }


    @Override
    public void setListener(IUserInterfaceContract.EventListener listener) {
        this.listener = listener;
    }

    public void initializeUserInterface() {
        drawBackground(root);
        drawTitle(root);
        drawSudokuBoard(root);
        drawTextFields(root);
        drawGridLines(root);
        drawStreakBox(root);
        drawTimerBox(root);
        drawCompletionButton(root);
        drawResetButton(root);
        drawQuitButton(root);

        stage.show();
    }



    /**
    To Both draw the numbers and handle inputs on the empty spaces
     */
    private void drawTextFields(Group root) {

        final int xOrigin = 50;
        final int yOrigin = 50;

        final int xAndYDelta = 64;


        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; yIndex++) {
                int x = xOrigin + xIndex * xAndYDelta;
                int y = yOrigin + yIndex * xAndYDelta;


                SudokuTextField tile = new SudokuTextField(xIndex, yIndex);


                styleSudokuTile(tile, x, y);


                tile.setOnKeyPressed(this);

                textFieldCoordinates.put(new Coordinates(xIndex, yIndex), tile);

                root.getChildren().add(tile);
            }
        }
    }

    /**
     To style the sudoku cells
     */
    private void styleSudokuTile(SudokuTextField tile, double x, double y) {
        Font numberFont = new Font(32);
        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);

        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefHeight(64);
        tile.setPrefWidth(64);

        tile.setBackground(Background.EMPTY);
    }


    /**
     To draw the Grid
     */
    private void drawGridLines(Group root) {
        int xAndY = 114;
        int index = 0;
        while (index < 8) {
            int thickness;
            if (index == 2 || index == 5) {
                thickness = 3;
            } else {
                thickness = 2;
            }

            Rectangle verticalLine = getLine(
                    xAndY + 64 * index,
                    BOARD_PADDING,
                    BOARD_X_AND_Y,
                    thickness
                    );

            Rectangle horizontalLine = getLine(
                    BOARD_PADDING,
                    xAndY + 64 * index,
                    thickness,
                    BOARD_X_AND_Y
            );

            root.getChildren().addAll(
                    verticalLine,
                    horizontalLine
            );

            index++;
        }
    }

    /**
     To Generate a Rectangle
     */
    public Rectangle getLine(double x, double y, double height, double width){
        Rectangle line = new Rectangle();

        line.setX(x);
        line.setY(y);

        line.setHeight(height);
        line.setWidth(width);

        line.setFill(Color.BLACK);
        return line;

    }

    /**
     * Background of the primary window

     */
    private void drawBackground(Group root) {
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
    }

    /**
     * Background of the actual sudoku board, offset from the window by BOARD_PADDING

     */
    private void drawSudokuBoard(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);
        boardBackground.setWidth(BOARD_X_AND_Y);
        boardBackground.setHeight(BOARD_X_AND_Y);
        boardBackground.setFill(BOARD_BACKGROUND_COLOR);
        root.getChildren().add(boardBackground);
    }

    /**
    To draw several visual components of the game
     */

    private void drawTitle(Group root) {
        Text title = new Text(235, 690, SUDOKU);
        title.setFill(Color.WHITE);
        Font titleFont = new Font(43);
        title.setFont(titleFont);
        root.getChildren().add(title);
    }


    private void drawStreakBox(Group root) {
         Text streakTitle = new Text (800, 200, StreakText);
         Font streakFont = new Font(30);
         streakTitle.setFill(Color.WHITE);
         streakTitle.setFont(streakFont);


         Text streak =  new Text(800, 264, String.valueOf(streakValueHolder));
         streak.setFill(Color.WHITE);
         streak.setFont(streakFont);

         root.getChildren().add(streakTitle);
         root.getChildren().add(streak);

    }

    private  void  drawTimerBox(Group root){
        Text timerTitle = new Text (800, 50, TimerText);
        Font timerFont = new Font(30);
        timerTitle.setFill(Color.WHITE);
        timerTitle.setFont(timerFont);


        double gameTime = 0.00;
        Text timerText =  new Text(800, 114, String.valueOf(gameTime));
        timerText.setFill(Color.WHITE);
        timerText.setFont(timerFont);

        root.getChildren().add(timerTitle);
        root.getChildren().add(timerText);

    }

    private  void  drawButton(int rectX, int rectY, String btnText){
        Rectangle button = new Rectangle (rectX, rectY, btnText.length()*10, 32 );
        button.setFill(Color.rgb(224, 242, 241));

        Text buttonText =  new Text(rectX + 10, rectY +20, btnText);
        Font buttonFont = new Font(15);
        buttonText.setFont(buttonFont);
        buttonText.setFill(Color.BLACK);


        root.getChildren().add(button);
        root.getChildren().add(buttonText);

    }



    private  void  drawResetButton(Group root){
        drawButton(800,400,"Generate New Puzzle");

    }




    private  void  drawQuitButton(Group root){
        drawButton(800,450,"Quit Game");

    }

    private void drawCompletionButton(Group root){

        drawButton(800, 500, "Complete Puzzle");
    }

    /**
    Updates the game in response to inputs
     */
    @Override
    public void updateSquare(int x, int y, int input) {
        SudokuTextField tile = textFieldCoordinates.get(new Coordinates(x, y));
        String value = Integer.toString(
                input
        );

        if (value.equals("0")) value = "";

        tile.textProperty().setValue(value);
    }

    @Override
    public void updateBoard(SudokuGame game) {
        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; yIndex++) {
                TextField tile = textFieldCoordinates.get(new Coordinates(xIndex, yIndex));

                String value = Integer.toString(
                        game.getCopyOfGridState()[xIndex][yIndex]
                );

                if (value.equals("0")) value = "";
                tile.setText(
                        value
                );


                if (game.getGameState() == GameState.NEW){
                    if (value.equals("")) {
                        tile.setStyle("-fx-opacity: 1;");
                        tile.setDisable(false);
                    } else {
                        tile.setStyle("-fx-opacity: 0.8;");
                        tile.setDisable(true);
                    }
                }
            }
        }
    }

    @Override
    public void showDialog(String message) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if (dialog.getResult() == ButtonType.OK) listener.onDialogClick();
    }

    @Override
    public void showError(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        dialog.showAndWait();
    }

    @Override
    public void updateStreak(Integer integer) {

    }

    @Override
    public  void updateBestTime(Duration duration) {

            Instant gameStart = null;
            Instant gameEnd = null;
            if(game.getGameState() == GameState.ACTIVE){
                gameStart = Instant.now();
            }

            if (game.getGameState() == GameState.COMPLETE){

                gameEnd = Instant.now();
            }
        Duration playtime = Duration.between(gameStart,gameEnd);


    }


    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (event.getText().equals("0")
                    || event.getText().equals("1")
                    || event.getText().equals("2")
                    || event.getText().equals("3")
                    || event.getText().equals("4")
                    || event.getText().equals("5")
                    || event.getText().equals("6")
                    || event.getText().equals("7")
                    || event.getText().equals("8")
                    || event.getText().equals("9")
            ) {
                int value = Integer.parseInt(event.getText());
                handleInput(value, event.getSource());
            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                handleInput(0, event.getSource());
            } else {
                ((TextField)event.getSource()).setText("");
            }
        }

        event.consume();
    }

    int streakValueHolder = userStreak();

    private int userStreak() {
        int gameStreak = 0;
        //gameStreak++;
        return gameStreak;
    }




    private void handleInput(int value, Object source) {
        listener.onSudokuInput(
                ((SudokuTextField) source).getX(),
                ((SudokuTextField) source).getY(),
                value
        );
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        this.game = game;
        int count = streakValueHolder;
        Instant gameStart = null;
        Instant gameEnd = null;

        if(game.getGameState() == GameState.ACTIVE){
            gameStart = Instant.now();
        }

        if (game.getGameState() == GameState.COMPLETE){
            streakValueHolder++;
            gameEnd = Instant.now();
        }
        Duration gameTime = Duration.between(gameStart, gameEnd);
    }
}
