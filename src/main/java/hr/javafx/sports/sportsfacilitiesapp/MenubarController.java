package hr.javafx.sports.sportsfacilitiesapp;

import hr.javafx.sports.account.AccountController;
import hr.javafx.sports.login.LoginManager;
import hr.javafx.sports.utils.DatabaseUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MenubarController extends AccountController {

    private String username;

    private String password;

    private Boolean isAdmin;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);

    private LoginManager loginManager = new LoginManager();

    @FXML
    private Button button;

    public void initialize(){
        this.username = AccountController.getUsername();
        this.password = AccountController.getPassword();
        this.isAdmin = AccountController.getRole();

    }

    public void showLoginScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            String message = "Error with showing login screen.";
            logger.error(message, e);
            System.out.println(message);
        }

        HelloApplication.getStage().setTitle("Sports Facilities Application - Signed out");
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }

    public void showSkiResortSearch(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("skiResortReserve.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            String message = "Error with showing ski resort.";
            logger.error(message, e);
            System.out.println(message);
        }
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }

    public void showAddSkiResort(){
        if(isAdmin.equals(true)){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addSkiResort.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 600, 400);
            } catch (IOException e) {
                String message = "Error with showing add ski resort.";
                logger.error(message, e);
                System.out.println(message);
            }
            HelloApplication.getStage().setScene(scene);
            HelloApplication.getStage().show();
        }
        else{
            addAlert();
        }


    }

    public void showPoolSearch(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("poolReserve.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            String message = "Error with showing pool search.";
            logger.error(message, e);
            System.out.println(message);
        }
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }

    public void showAddPool(){
        if(isAdmin.equals(true)){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addPool.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 600, 400);
            } catch (IOException e) {
                String message = "Error with showing add pool.";
                logger.error(message, e);
                System.out.println(message);
            }
            HelloApplication.getStage().setScene(scene);
            HelloApplication.getStage().show();
        }
        else{
            addAlert();
        }
    }

    public void showSkatingRingSearch(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("skatingRingReserve.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            String message = "Error with showing skating ring.";
            logger.error(message, e);
            System.out.println(message);
        }
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }

    public void showAddSkatingRing(){
        if(isAdmin.equals(true)){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addSkatingRing.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 600, 400);
            } catch (IOException e) {
                String message = "Error with showing add skating ring.";
                logger.error(message, e);
                System.out.println(message);
            }
            HelloApplication.getStage().setScene(scene);
            HelloApplication.getStage().show();
        }
        else{
            addAlert();
        }
    }

    public void showStadiumSearch(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("stadiumReserve.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            String message = "Error with showing stadium.";
            logger.error(message, e);
            System.out.println(message);
        }
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }

    public void showAddStadium(){
        if(isAdmin.equals(true)){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addStadium.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 600, 400);
            } catch (IOException e) {
                String message = "Error with showing add stadium.";
                logger.error(message, e);
                System.out.println(message);
            }
            HelloApplication.getStage().setScene(scene);
            HelloApplication.getStage().show();
        }
        else{
            addAlert();
        }

    }

    public void showChangelog(){
        if(isAdmin.equals(true)){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("changelog.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 600, 400);
            } catch (IOException e) {
                String message = "Error with showing changelog.";
                logger.error(message, e);
                System.out.println(message);
            }
            HelloApplication.getStage().setScene(scene);
            HelloApplication.getStage().show();
        }
        else{
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Permission denied");
                alert.setHeaderText("Permission denied");
                alert.setContentText("You must be logged in as admin to access the changelog.");
                alert.showAndWait();
            });
        }




    }

    public void showEditPool(){
        if(isAdmin.equals(true)){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editDeletePool.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 600, 400);
            } catch (IOException e) {
                String message = "Error with showing edit pool.";
                logger.error(message, e);
                System.out.println(message);
            }
            HelloApplication.getStage().setScene(scene);
            HelloApplication.getStage().show();
        }
        else{
            editAlert();
        }

    }



    public void showEditSkatingRing(){
        if(isAdmin.equals(true)){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editDeleteSkatingRing.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 600, 400);
            } catch (IOException e) {
                String message = "Error with edit skating ring.";
                logger.error(message, e);
                System.out.println(message);
            }
            HelloApplication.getStage().setScene(scene);
            HelloApplication.getStage().show();
        }
        else{
            editAlert();
        }

    }


    public void showEditSkiResort(){
        if(isAdmin.equals(true)){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editDeleteSkiResort.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 600, 400);
            } catch (IOException e) {
                String message = "Error with edit ski resort.";
                logger.error(message, e);
                System.out.println(message);
            }
            HelloApplication.getStage().setScene(scene);
            HelloApplication.getStage().show();
        }
        else{
            editAlert();
        }

    }



    public void showEditStadium(){
        if(isAdmin.equals(true)){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editDeleteStadium.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 600, 400);
            } catch (IOException e) {
                String message = "Error with edit stadium.";
                logger.error(message, e);
                System.out.println(message);
            }
            HelloApplication.getStage().setScene(scene);
            HelloApplication.getStage().show();
        }
        else{
            editAlert();
        }

    }



    public void addAlert(){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Permission denied");
            alert.setHeaderText("Permission denied");
            alert.setContentText("You must be logged in as admin to add reservations.");
            alert.showAndWait();
        });
    }

    public void editAlert(){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Permission denied");
            alert.setHeaderText("Permission denied");
            alert.setContentText("You must be logged in as admin to edit reservations.");
            alert.showAndWait();
        });
    }


}
