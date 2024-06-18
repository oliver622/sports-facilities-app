package hr.javafx.sports.sportsfacilitiesapp;

import hr.javafx.sports.account.AccountController;
import hr.javafx.sports.alerts.Alerts;
import hr.javafx.sports.domain.Pool;
import hr.javafx.sports.domain.SkatingRing;
import hr.javafx.sports.threads.SearchPoolThread;
import hr.javafx.sports.utils.DatabaseUtils;
import hr.javafx.sports.validate.Validate;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class PoolReserveController {
    @FXML
    private TextField textFieldPoolName;

    @FXML
    private TextField textFieldDepth;

    @FXML
    private TextField textFieldIdToReserve;

    @FXML
    private TableView<Pool> poolTableView;

    @FXML
    private TableColumn<Pool, String> idTableColumn;

    @FXML
    private TableColumn<Pool, String> nameTableColumn;

    @FXML
    private TableColumn<Pool, String> depthTableColumn;

    @FXML
    private TableColumn<Pool, String> dateTableColumn;

    @FXML
    private TableColumn<Pool, String> isReservedTableColumn;


    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);



    public void initialize(){

        idTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pool,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pool,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getId().toString());
            }
        });

        nameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pool,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pool,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getName());
            }
        });

        depthTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pool,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pool,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getDepth().toString());
            }
        });

        dateTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pool,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pool,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getDate());
            }
        });

        isReservedTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pool,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pool,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getReserved());
            }
        });

        List<Pool> poolList = DatabaseUtils.getAllPools();

        ObservableList observablePoolList =  FXCollections.observableArrayList(poolList);

        poolTableView.setItems(observablePoolList);

        SearchPoolThread thread = new SearchPoolThread(poolTableView, textFieldPoolName,textFieldDepth);
        Thread starter = new Thread(thread);
        starter.start();
    }







    public static List<Pool> filterPools(List<Pool> poolList, String poolNameFilter, String depthFilter) {

        return poolList.stream()
                .filter(pool -> pool.getName().toLowerCase().contains(poolNameFilter.toLowerCase()))
                .filter(pool -> pool.getDepth().toString().contains(depthFilter))
                .collect(Collectors.toList());


    }



    public static List<Pool> filterAndSortByDepth(List<Pool> poolList, String poolNameFilter, String depthFilter) {
        Set<String> filteredNames = poolList.stream()
                .filter(pool -> pool.getName().toLowerCase().contains(poolNameFilter.toLowerCase()))
                .map(Pool::getName)
                .collect(Collectors.toSet());

        Map<String, List<Pool>> filteredPoolsByName = poolList.stream()
                .filter(pool -> filteredNames.contains(pool.getName()))
                .filter(pool -> pool.getDepth().toString().contains(depthFilter))
                .collect(Collectors.groupingBy(Pool::getName));

        return filteredPoolsByName.values().stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparing(Pool::getDepth))
                .collect(Collectors.toList());
    }







    public void reserveById(){
        String username = AccountController.getUsername();

        String stringId = textFieldIdToReserve.getText();
        Optional<Long> idToEdit = Validate.validateId(stringId, "POOL");

        if(idToEdit.isEmpty()){
            return;
        }

        if(Alerts.showConfirmation("Are you sure you want to reserve/unreserve?").equals(false)){
            return;
        }

        try{
            DatabaseUtils.reserveColumn("POOL", username, Long.parseLong(stringId));
        }
        catch (NumberFormatException e) {
            System.out.println("Error parsing ID from textField: " + stringId);
            e.printStackTrace();
        }

        List<Pool> poolList = DatabaseUtils.getAllPools();

        ObservableList observablePoolList =  FXCollections.observableArrayList(poolList);

        poolTableView.setItems(observablePoolList);


    }

}
