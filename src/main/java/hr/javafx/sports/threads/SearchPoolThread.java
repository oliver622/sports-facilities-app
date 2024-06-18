package hr.javafx.sports.threads;

import hr.javafx.sports.domain.Pool;
import hr.javafx.sports.sportsfacilitiesapp.PoolReserveController;
import hr.javafx.sports.utils.DatabaseUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SearchPoolThread extends DatabaseThread implements Runnable{


    private TableView<Pool> pools;

    private TextField textFieldName;

    private TextField textFieldDepth;


    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);


    public SearchPoolThread(TableView<Pool> pools, TextField textFieldName, TextField textFieldDepth) {
        this.pools = pools;
        this.textFieldName = textFieldName;
        this.textFieldDepth = textFieldDepth;
    }



    @Override
    public void run() {
        while(true){
            String name = textFieldName.getText();
            String depth = textFieldDepth.getText();

            List<Pool> filteredPools = PoolReserveController.filterPools(super.getAllPools(), name, depth);
            List<Pool> sortedPools = PoolReserveController.filterAndSortByDepth(filteredPools,name,depth);

            Platform.runLater(() -> {
                pools.setItems(FXCollections.observableArrayList(sortedPools));
            });

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                String message = "Error with search pool thread.";
                logger.error(message, e);
                System.out.println(message);
            }
        }


    }

}
