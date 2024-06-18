package hr.javafx.sports.alerts;

import hr.javafx.sports.account.AccountController;
import hr.javafx.sports.changelog.ChangelogEntry;
import hr.javafx.sports.utils.DatabaseUtils;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Alerts {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);
    public static void showAlert(String titleAndHeaderText, String contentText){
        Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle(titleAndHeaderText);
            alert.setHeaderText(titleAndHeaderText);
            alert.setContentText(contentText);
            alert.showAndWait();
        });
    }

    public static void showAlertToChangelog(String titleAndHeaderText, String contentText){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd HH:mm:ss]");
        LocalDateTime formattedDateTime = LocalDateTime.now();
        String formattedDateTimeString = formattedDateTime.format(formatter);

        Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle(titleAndHeaderText);
            alert.setHeaderText(titleAndHeaderText);
            alert.setContentText("[" + formattedDateTimeString + "] " + contentText + " by " + AccountController.getUsername());
            alert.showAndWait();
        });

        ChangelogEntry entry = new ChangelogEntry(formattedDateTime, AccountController.getUsername(), contentText);
        writeContentToChangelog(entry);
    }



    private static void writeContentToChangelog(ChangelogEntry entry) {

        Path filePath = Paths.get("logs/changelog.ser");

        try {
            List<ChangelogEntry> entries;
            if (Files.exists(filePath)) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
                    entries = (List<ChangelogEntry>) ois.readObject();
                }
            } else {
                entries = new ArrayList<>();
            }

            entries.add(entry);

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
                oos.writeObject(entries);
            }
        } catch (IOException | ClassNotFoundException e) {
            String message = "Error with serializing changelog.";
            logger.error(message, e);
            System.out.println(message);
        }
    }


    public static Boolean showConfirmation(String confirmationText) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Confirmation");
        confirmationAlert.setContentText(confirmationText);

        DialogPane dialogPane = confirmationAlert.getDialogPane();

        return confirmationAlert.showAndWait()
                .map(response -> response == ButtonType.OK)
                .orElse(false);
    }

}
