package controllers;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Cypher;
import util.EmailUtil;
import util.FormValidate;

/**
 * FXML Controller class
 *
 * @author Mahendra Tennakoon
 */
public class PasswordRecoveryController implements Initializable {

    static final Logger LOG = LoggerFactory.getLogger(PasswordRecoveryController.class);

    @FXML
    private AnchorPane card_step_1;
    @FXML
    private AnchorPane card_step_2;
    @FXML
    private Label lbl_steps;
    @FXML
    private ProgressBar progress_bar;
    @FXML
    private TextField txt_user_name;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_recovery_token;
    @FXML
    private Button btn_reset_password;
    @FXML
    private TextField txt_new_password;
    @FXML
    private TextField txt_confirm_password;

    private static String reset_token;
    private static String user_name;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Hide the top most anchor pane and make the second pane visible.
     */
    public void gotoStep2() {
        card_step_1.setVisible(false);
        card_step_2.setVisible(true);
        lbl_steps.setText("Step 2 of 2");
        progress_bar.setProgress(1.0);
    }

    /**
     * validate username and email address associated with it.
     *
     * @return
     */
    private boolean validateUsernameAndEmail() {
        user_name = txt_user_name.getText();
        String email = txt_email.getText();

        if (FormValidate.isEmptyField(user_name, "User name") || FormValidate.isEmptyField(email, "Email address")) {
            return false;
        } else if (!FormValidate.validateEmail(email)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid email address.");
            alert.showAndWait();
            return false;
        } else if (!crossReferenceEmail(user_name, email)) {
            return false;
        }
        return true;
    }

    /**
     * Cross reference the input email address against the stored email in db.
     *
     * @param username
     * @param email
     * @return
     */
    private boolean crossReferenceEmail(String username, String email) {
        UserDAO user = new UserDAO();
        user.setup();
        String stored_email = user.read(username).getEmail();
        user.exit();

        if (email.equals(stored_email)) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There is no such email address associated with the user name you provided.");
            alert.showAndWait();
        }
        return false;
    }

    /**
     * email the password reset token.
     */
    public void sendResetToken() {
        user_name = txt_user_name.getText();
        String emailAddress = txt_email.getText();

        LOG.info("Password recovery attempt for user " + user_name);

        EmailUtil emailUtil = new EmailUtil();

        if (validateUsernameAndEmail()) {
            reset_token = generateRandomToken();

            boolean response = emailUtil.sendPasswordResetEmail(user_name, emailAddress, reset_token);
            if (response) {
                txt_recovery_token.setDisable(false);
                btn_reset_password.setDisable(false);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("A network error occurred.");
                alert.showAndWait();
            }
        }
    }

    /**
     * Generate a random string token (UUID).
     *
     * @return
     */
    public String generateRandomToken() {
        return UUID.randomUUID().toString();
    }

    /**
     * compare the reset token entered by user against the generated version.
     */
    public void validateResetToken() {
        String input_token = txt_recovery_token.getText();
        if (!FormValidate.isEmptyField(input_token, "Password reset token")) {
            if (input_token.equals(reset_token)) {
                gotoStep2();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid password recovery token.");
                alert.showAndWait();
            }
        }
    }

    /**
     * Validate the newly entered password.
     */
    public void validateNewPassword() {
        String password = txt_new_password.getText();
        String confirmPassword = txt_confirm_password.getText();

        if (!FormValidate.isEmptyField(password, "New password") && !FormValidate.isEmptyField(confirmPassword, "Confirm password field")) {
            if (FormValidate.validatePassword(password, confirmPassword)) {
                String hashedPassword = Cypher.generateMD5(password);

                UserDAO userDAO = new UserDAO();
                userDAO.setup();
                UserEntity user = userDAO.read(user_name);
                user.setPassword(hashedPassword);
                userDAO.update(user);
                userDAO.exit();
                goBackToLogin();
            }
        }
    }

    /**
     * Close the current window show a new instance of the login screen.
     */
    public void goBackToLogin() {
        Stage currentStage = (Stage) btn_reset_password.getScene().getWindow();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
            Scene loginScene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(loginScene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

            // close password recovery stage.
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
