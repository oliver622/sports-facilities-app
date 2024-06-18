package hr.javafx.sports.threads;

import hr.javafx.sports.domain.Pool;
import hr.javafx.sports.domain.SkatingRing;
import hr.javafx.sports.sportsfacilitiesapp.PoolReserveController;
import hr.javafx.sports.sportsfacilitiesapp.SkatingRingReserveController;
import hr.javafx.sports.threads.DatabaseThread;
import hr.javafx.sports.utils.DatabaseUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SearchSkatingRingThread extends DatabaseThread implements Runnable{


    private TableView<SkatingRing> skatingrings;

    private TextField textFieldName;

    private TextField textFieldTemperature;



    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);


    public SearchSkatingRingThread(TableView<SkatingRing> skatingrings, TextField textFieldName, TextField textFieldTemperature) {
        this.skatingrings = skatingrings;
        this.textFieldName = textFieldName;
        this.textFieldTemperature = textFieldTemperature;
    }



    @Override
    public void run() {
        while(true){
            String name = textFieldName.getText();
            String temperature = textFieldTemperature.getText();

            List<SkatingRing> filteredSkatingRings = SkatingRingReserveController.filterSkatingRings(super.getAllSkatingRings(), name, temperature);
            List<SkatingRing> sortedSkatingRings = SkatingRingReserveController.filterAndSortByHumidity(filteredSkatingRings,name,temperature);

            Platform.runLater(() -> {
                skatingrings.setItems(FXCollections.observableArrayList(sortedSkatingRings));
            });

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                String message = "Error with skating ring search thread.";
                logger.error(message, e);
                System.out.println(message);
            }
        }


    }

}
