package com.models;

import javafx.scene.layout.GridPane;

public class sudoku {

    private int[][] board;
    private int[][] tempBoard;
    private static final int BOARD_START_INDEX = 0;
    private static final int BOARD_END_INDEX = 9;
    private static final int EMPTY_CELL = 0;

    public int[][] getTempBoard() {
        return this.tempBoard;
    }

    public void setTempBoard(int[][] tempBoard) {
        this.tempBoard = tempBoard;
    }


    public int[][] getBoard() {
        return this.board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public boolean solve(){
        for(int row = BOARD_START_INDEX; row < BOARD_END_INDEX; row++){
            for(int col = BOARD_START_INDEX; col < BOARD_END_INDEX; col++){
                if(this.board[row][col] == EMPTY_CELL){
                    for(int number = 1; number <= BOARD_END_INDEX; number++){
                        if(isValid(row, col, number)){
                            this.board[row][col] = number;
                            if(solve()){
                                return true;
                            } else {
                                this.board[row][col] = EMPTY_CELL;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int row, int col, int number){
        return !isInRow(row, number) && !isInCol(col, number) && !isInBox(row, col, number);
    }

    private boolean isInRow(int row, int number){
        for(int i = BOARD_START_INDEX; i < BOARD_END_INDEX; i++){
            if(this.board[row][i] == number){
                return true;
            }
        }
        return false;
    }

    private boolean isInCol(int col, int number){
        for(int i = BOARD_START_INDEX; i < BOARD_END_INDEX; i++){
            if(this.board[i][col] == number){
                return true;
            }
        }
        return false;
    }

    private boolean isInBox(int row, int col, int number){
        int r = row - row % 3;
        int c = col - col % 3;
        for(int i = r; i < r + 3; i++){
            for(int j = c; j < c + 3; j++){
                if(this.board[i][j] == number){
                    return true;
                }
            }
        }
        return false;
    }
    
}
