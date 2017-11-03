package controllers;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.UserEntity;

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
    @FXML
    private TextField txt_user_name;
    @FXML
    private TextField txt_password;

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
        String username = txt_user_name.getText();
        String password = txt_password.getText();

        if (validateCredentails(username, password)) {
            authenticationError = Boolean.FALSE;
            displayHome();
            // TODO: display home
        } else {
            authenticationError = Boolean.TRUE;
            processAuthError();
        }
    }

    /**
     * Close the login stage and display 'home' for the authenticated user.
     */
    private void displayHome() {

    }

    /**
     * Validate login credentials.
     * @param userName
     * @param password
     */
    private boolean validateCredentails(String userName, String password) {
        UserDAO userDAO = new UserDAO();
        userDAO.setup();
        UserEntity user = userDAO.read(userName);
        userDAO.exit();

        //TODO: md5 encrypt password

        if (user.getPassword().equals(password)) {
            return true;
        }
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
