package hr.javafx.sports.threads;

import hr.javafx.sports.domain.SkiResort;
import hr.javafx.sports.domain.Stadium;
import hr.javafx.sports.sportsfacilitiesapp.SkiResortReserveController;
import hr.javafx.sports.sportsfacilitiesapp.StadiumReserveController;
import hr.javafx.sports.utils.DatabaseUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SearchStadiumThread extends DatabaseThread implements Runnable{

    private TableView<Stadium> stadiums;

    private TextField textFieldName;

    private TextField textFieldCapacity;


    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);


    public SearchStadiumThread(TableView<Stadium> stadiums, TextField textFieldName, TextField textFieldCapacity) {
        this.stadiums = stadiums;
        this.textFieldName = textFieldName;
        this.textFieldCapacity = textFieldCapacity;
    }



    @Override
    public void run() {
        while(true){
            String name = textFieldName.getText();
            String capacity = textFieldCapacity.getText();

            List<Stadium> filteredStadiums = StadiumReserveController.filterStadiums(super.getAllStadiums(), name, capacity);
            List<Stadium> sortedStadiums = StadiumReserveController.filterAndSortByCapacity(filteredStadiums, name, capacity);

            Platform.runLater(() -> {
                stadiums.setItems(FXCollections.observableArrayList(sortedStadiums));
            });

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                String message = "Error with stadium search thread.";
                logger.error(message, e);
                System.out.println(message);
            }
        }


    }

}


