package com.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SettingsController {

    private mainViewController mainViewController;

    @FXML
    private Button buttonSettingsBack;

    @FXML
    private Button buttonSettingsSave;

    @FXML
    private ChoiceBox<String> choiceBoxThemes;

    @FXML
    private Label labelCurrentTheme;

    // ---------------------------------------------------------------------------------------

    public void initialize() {
        populateChoiceBoxThemes();

    }

    public void populateChoiceBoxThemes() {
        ObservableList<String> themes = FXCollections.observableArrayList("Light", "Dark", "Matrix", "Teal", "Äcklig",
                "Purple");
        choiceBoxThemes.setItems(themes);

    }

    @FXML
    void handleButtonSettingsBack(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

    }

    @FXML
    void handleButtonSettingsSave(ActionEvent event) {
        String theme = choiceBoxThemes.getValue();
        setCSSForHomeView(theme);
        labelCurrentTheme.setText("Current theme: " + theme);

    }

    public void setMainViewController(mainViewController mainViewController) {
        this.mainViewController = mainViewController;

    }

    public void setCSSForHomeView(String theme) {
        if (mainViewController != null) {
            mainViewController.setCSSFile(theme);

        }
    }

}
