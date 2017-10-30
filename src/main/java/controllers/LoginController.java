package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginController {
    public static final int MAX_LOGIN_ATTEMPTS = 5;

    private int attemptsLeft;
    private boolean authenticationError;

    @FXML
    private Button btn_close;
    @FXML
    private Label msg_auth_error;
    @FXML
    private Label msg_attempts_left;

    /**
     * Called automatically when the FXML is loaded.
     * Initialize login stage and reset all counters and messages.
     */
    public void initialize(){
        attemptsLeft = MAX_LOGIN_ATTEMPTS;
        authenticationError = Boolean.FALSE;
        updateMessages();
    }

    /**
     * Close the login stage.
     */
    public void closeLogin() {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }

    /**
     * Update info/error messages on login stage.
     */
    private void updateMessages() {
        // Authentication error message.
        if (authenticationError == Boolean.FALSE) {
            msg_auth_error.setVisible(false);
        } else {
            msg_auth_error.setVisible(true);
        }

        if (attemptsLeft == MAX_LOGIN_ATTEMPTS) {
            msg_attempts_left.setVisible(false);
        } else {
            msg_attempts_left.setText("Attempts left: " + attemptsLeft);
            msg_attempts_left.setVisible(true);
        }
    }

    /**
     * Deduct an attempt.
     */
    private void updateAttemptsLeft() {
        if (attemptsLeft != 0) {
            attemptsLeft--;
        }
    }

    /**
     * Log in to the system if the credentials are correct.
     * If not, update relevant messages.
     */
    public void login() {
        if (validateCredentails("username", "password")) {
            authenticationError = Boolean.FALSE;
            // TODO: display home
        } else {
            authenticationError = Boolean.TRUE;
            processAuthError();
        }
    }

    /**
     * Validate login credentials.
     * @param userName
     * @param password
     */
    private boolean validateCredentails(String userName, String password) {
        // TODO: implement
        authenticationError = true;
        return false;
    }

    /**
     * Update attempts left and if maximum allowed attempts is reached, display error and exit.
     */
    private void processAuthError() {
        updateAttemptsLeft();
        if (attemptsLeft == 0) {
            // TODO: show error
            closeLogin();
        } else {
            updateMessages();
        }
    }
}
