package com.controllers;

import com.models.sudoku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.scene.Node;

public class mainViewController {

    private sudoku sudoku;

    @FXML
    private AnchorPane backgroundAnchorPane;

    @FXML
    private Button buttonClear;

    @FXML
    private Button buttonSolve;

    @FXML
    private GridPane gridpaneSudoku;

    @FXML
    private AnchorPane sudokuAcnhorPane;

    @FXML
    private HBox buttonHbox;

    @FXML
    private Button buttonBack;

    public void initialize() {
        populateGridPaneWithTextField();
    }

    @FXML
    void handleButtonClearAction(ActionEvent event) {
        gridpaneSudoku.getChildren().clear();
        populateGridPaneWithTextField();

    }

    @FXML
    void handleButtonBackAction(ActionEvent event) {
        gridpaneSudoku.getChildren().clear();
        populateGridPaneWithTextField();
        returnToInputedNumbers();
    }

    @FXML
    void handleButtonSolveAction(ActionEvent event) {
        try {
            int[][] board = new int[9][9];
            int[][] tempBoard = new int[9][9];

            // Read the current state of the grid and populate the board array
            readGridState(board);
            readGridState(tempBoard);
            // Set the board to the sudoku solver and solve
            sudoku.setBoard(board);
            sudoku.setTempBoard(tempBoard);
            if (sudoku.solve()) {
                // Update the grid with the solution
                updateGridWithSolution();
            } else {
                alert("No solution found");
            }
        } catch (NumberFormatException e) {
            alert("Invalid input: Please enter only numbers.");
        } catch (IllegalArgumentException e) {
            alert("Invalid Sudoku state: " + e.getMessage());
        } catch (Exception e) {
            alert("An error occurred: " + e.getMessage());
        }
    }

    private void readGridState(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField textField = (TextField) getNodeByRowColumnIndex(row, col, gridpaneSudoku);
                String text = textField.getText();
                if (!text.isEmpty()) {
                    int num = Integer.parseInt(text);
                    if (num < 1 || num > 9 || !isValidPlacement(board, row, col, num)) {
                        // Throw an exception if input is invalid
                        throw new IllegalArgumentException(
                                "Invalid input at row " + (row + 1) + ", column " + (col + 1));
                    }
                    board[row][col] = num;
                } else {
                    board[row][col] = 0; // Empty cell
                }
            }
        }
    }

    private void updateGridWithSolution() {
        int[][] board = sudoku.getBoard();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField textField = (TextField) getNodeByRowColumnIndex(row, col, gridpaneSudoku);
                textField.setText(Integer.toString(board[row][col]));
                textField.setEditable(false);

            }
        }
    }

    private void returnToInputedNumbers() {
        int[][] board = sudoku.getTempBoard();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if(board[row][col] == 0){
                    continue;
                }
                TextField textField = (TextField) getNodeByRowColumnIndex(row, col, gridpaneSudoku);
                textField.setText(Integer.toString(board[row][col]));
                textField.setEditable(true);

            }
        }
    }

    private boolean isValidPlacement(int[][] board, int row, int col, int num) {
        // Check if the number is already in the row, column, or 3x3 subgrid
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num ||
                    board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == num) {
                return false;
            }
        }
        return true;
    }

    private void populateGridPaneWithTextField() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField textField = new TextField();
                textField.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                textField.setEditable(true);
                textField.setAlignment(Pos.CENTER); // Center align the text
                // Restrict input to a single numeric character
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) { // Allow only digits
                        textField.setText(newValue.replaceAll("[^\\d]", ""));

                    }

                    if (textField.getText().length() > 1) { // Limit to single digit
                        textField.setText(newValue.substring(0, 1));
                    }
                });

                setTextAreaColor(textField, row, col); // Assuming this method is adjusted for TextField
                gridpaneSudoku.add(textField, col, row);
            }
        }
    }

    private TextField getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        TextField result = null;
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = (TextField) node;
                break;
            }
        }
        return result;
    }

    private void setTextAreaColor(TextField textField, int row, int col) {
        String backgroundColor;
        if ((row / 3 + col / 3) % 2 == 0) {
            backgroundColor = "#f0f0f0"; // Light Grey
        } else {
            backgroundColor = "#ADD8E6"; // Pastel Blue
        }
        textField.setStyle("-fx-control-inner-background: " + backgroundColor + ";");
    }

    public void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sudoku");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(backgroundAnchorPane.getScene().getWindow());
        alert.showAndWait();

    }

    public void setSudoku(sudoku sudoku) {
        this.sudoku = sudoku;
    }
}
