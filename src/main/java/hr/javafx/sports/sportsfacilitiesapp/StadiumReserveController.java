package hr.javafx.sports.sportsfacilitiesapp;

import hr.javafx.sports.account.AccountController;
import hr.javafx.sports.alerts.Alerts;
import hr.javafx.sports.domain.Pool;
import hr.javafx.sports.domain.SkiResort;
import hr.javafx.sports.domain.Stadium;
import hr.javafx.sports.domain.Stadium;
import hr.javafx.sports.threads.SearchSkiResortThread;
import hr.javafx.sports.threads.SearchStadiumThread;
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

public class StadiumReserveController {

    @FXML
    private TextField textFieldStadiumName;

    @FXML
    private TextField textFieldAudienceCapacity;

    @FXML
    private TextField textFieldIdToReserve;

    @FXML
    private TableView<Stadium> stadiumTableView;

    @FXML
    private TableColumn<Stadium, String> idTableColumn;

    @FXML
    private TableColumn<Stadium, String> nameTableColumn;

    @FXML
    private TableColumn<Stadium, String> audienceCapacityTableColumn;

    @FXML
    private TableColumn<Stadium, String> dateTableColumn;

    @FXML
    private TableColumn<Stadium, String> isReservedTableColumn;


    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);


    public void initialize(){

        idTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stadium,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stadium,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getId().toString());
            }
        });

        nameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stadium,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stadium,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getName());
            }
        });

        audienceCapacityTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stadium,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stadium,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getAudienceCapacity().toString());
            }
        });

        dateTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stadium,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stadium,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getDate());
            }
        });

        isReservedTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stadium,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stadium,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getReserved());
            }
        });


        List<Stadium> stadiumList = DatabaseUtils.getAllStadiums();

        ObservableList observableStadiumList =  FXCollections.observableArrayList(stadiumList);

        stadiumTableView.setItems(observableStadiumList);

        SearchStadiumThread thread = new SearchStadiumThread(stadiumTableView, textFieldStadiumName,textFieldAudienceCapacity);
        Thread starter = new Thread(thread);
        starter.start();


    }

    public static List<Stadium> filterStadiums(List<Stadium> stadiumList, String stadiumNameFilter, String capacityFilter) {

        return stadiumList.stream()
                .filter(stadium -> stadium.getName().toLowerCase().contains(stadiumNameFilter.toLowerCase()))
                .filter(stadium -> stadium.getAudienceCapacity().toString().contains(capacityFilter))
                .collect(Collectors.toList());


    }

    public static List<Stadium> filterAndSortByCapacity(List<Stadium> stadiumList, String stadiumNameFilter, String capacityFilter) {
        Set<String> filteredNames = stadiumList.stream()
                .filter(stadium -> stadium.getName().toLowerCase().contains(stadiumNameFilter.toLowerCase()))
                .map(Stadium::getName)
                .collect(Collectors.toSet());

        Map<String, List<Stadium>> filteredStadiumsByName = stadiumList.stream()
                .filter(stadium -> filteredNames.contains(stadium.getName()))
                .filter(stadium -> stadium.getAudienceCapacity().toString().contains(capacityFilter))
                .collect(Collectors.groupingBy(Stadium::getName));

        return filteredStadiumsByName.values().stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparing(Stadium::getAudienceCapacity))
                .collect(Collectors.toList());
    }





    public void reserveById(){
        String username = AccountController.getUsername();

        String stringId = textFieldIdToReserve.getText();
        Optional<Long> idToEdit = Validate.validateId(stringId, "STADIUM");

        if(idToEdit.isEmpty()){
            return;
        }

        if(Alerts.showConfirmation("Are you sure you want to reserve/unreserve?").equals(false)){
            return;
        }

        try{
            DatabaseUtils.reserveColumn("STADIUM", username, Long.parseLong(stringId));
        }
        catch (NumberFormatException e) {
            System.out.println("Error parsing ID from textField: " + stringId);
            e.printStackTrace();
        }

        List<Stadium> stadiumList = DatabaseUtils.getAllStadiums();

        ObservableList observableStadiumList =  FXCollections.observableArrayList(stadiumList);

        stadiumTableView.setItems(observableStadiumList);
    }

}
