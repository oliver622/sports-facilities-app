package hr.javafx.sports.threads;

import hr.javafx.sports.domain.Pool;
import hr.javafx.sports.domain.SkatingRing;
import hr.javafx.sports.domain.SkiResort;
import hr.javafx.sports.sportsfacilitiesapp.PoolReserveController;
import hr.javafx.sports.sportsfacilitiesapp.SkatingRingReserveController;
import hr.javafx.sports.sportsfacilitiesapp.SkiResortReserveController;
import hr.javafx.sports.threads.DatabaseThread;
import hr.javafx.sports.utils.DatabaseUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SearchSkiResortThread extends DatabaseThread implements Runnable{


    private TableView<SkiResort> skiresorts;

    private TextField textFieldName;

    private TextField textFieldHumidity;


    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);


    public SearchSkiResortThread(TableView<SkiResort> skiresorts, TextField textFieldName, TextField textFieldHumidity) {
        this.skiresorts = skiresorts;
        this.textFieldName = textFieldName;
        this.textFieldHumidity = textFieldHumidity;
    }



    @Override
    public void run() {
        while(true){
            String name = textFieldName.getText();
            String humidity = textFieldHumidity.getText();

            List<SkiResort> filteredSkiResorts = SkiResortReserveController.filterSkiResorts(super.getAllSkiResorts(), name, humidity);
            List<SkiResort> sortedSkiResorts = SkiResortReserveController.filterAndSortByHumidity(filteredSkiResorts,name,humidity);

            Platform.runLater(() -> {
                skiresorts.setItems(FXCollections.observableArrayList(sortedSkiResorts));
            });

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                String message = "Error with ski resort search thread.";
                logger.error(message, e);
                System.out.println(message);
            }
        }


    }

}