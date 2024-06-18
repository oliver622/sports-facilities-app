package hr.javafx.sports.sportsfacilitiesapp;

import hr.javafx.sports.changelog.ChangelogEntry;
import hr.javafx.sports.utils.DatabaseUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public non-sealed class ChangelogController implements LoadChangelogInterface{

    @FXML
    private TextArea changelogTextArea;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);


    public void initialize(){
        changelogTextArea.setEditable(false);
        changelogTextArea.setFocusTraversable(false);

        loadChangelog();
    }

    @Override
    public void loadChangelog() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("logs/changelog.ser"))) {
            List<ChangelogEntry> entries = (List<ChangelogEntry>) ois.readObject();

            StringBuilder formattedEntries = new StringBuilder();

            for (ChangelogEntry entry : entries) {
                String formattedEntry = "[" + entry.dateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                        "] " + entry.content() + " by " + entry.username() + "\n";
                formattedEntries.append(formattedEntry);
            }

            changelogTextArea.setText(formattedEntries.toString());
        } catch (IOException | ClassNotFoundException e) {
            String message = "Error with loading changelog.";
            logger.error(message, e);
            System.out.println(message);
        }
    }

}
