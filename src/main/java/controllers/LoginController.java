package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private Button btn_close;

    public void closeLogin() {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }
}
