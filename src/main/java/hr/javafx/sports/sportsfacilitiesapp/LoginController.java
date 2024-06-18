package hr.javafx.sports.sportsfacilitiesapp;

import hr.javafx.sports.account.AccountController;
import hr.javafx.sports.utils.DatabaseUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import hr.javafx.sports.login.LoginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoginController implements SignInInterface {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    public Boolean isAdmin;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);


    public void initialize(){

    }




    @Override
    public void signIn(){
        if (usernameField == null || passwordField == null) {
            throw new IllegalStateException("Text fields are not properly initialized.");
        }

        String enteredUsername = usernameField.getText();
        String enteredPassword = passwordField.getText();

        if (LoginManager.authenticateUser(enteredUsername, enteredPassword)) {
            isAdmin = LoginManager.isAdmin(enteredUsername,enteredPassword);
            AccountController accountController = new AccountController(enteredUsername, enteredPassword, isAdmin);
            navigateToMainMenu();
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid credentials");
                alert.setHeaderText("Invalid credentials");
                alert.setContentText("Invalid username or password, please try again.");
                alert.showAndWait();
            });
        }

    }

    private void navigateToMainMenu() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("skiResortReserve.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            String message = "Error with navigating to main menu.";
            logger.error(message, e);
            System.out.println(message);
        }

        HelloApplication.getStage().setTitle("Sports Facilities Application - Logged in as "+ AccountController.getUsername());
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }

}



