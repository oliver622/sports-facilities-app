package hr.javafx.sports.sportsfacilitiesapp;

import hr.javafx.sports.account.AccountController;
import hr.javafx.sports.alerts.Alerts;
import hr.javafx.sports.domain.Pool;
import hr.javafx.sports.domain.SkatingRing;
import hr.javafx.sports.threads.SearchSkatingRingThread;
import hr.javafx.sports.utils.DatabaseUtils;
import hr.javafx.sports.validate.Validate;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class SkatingRingReserveController {

    @FXML
    private TextField textFieldSkatingRingName;

    @FXML
    private TextField textFieldTemperature;

    @FXML
    private TextField textFieldIdToReserve;

    @FXML
    private TableView<SkatingRing> skatingRingTableView;

    @FXML
    private TableColumn<SkatingRing, String> idTableColumn;

    @FXML
    private TableColumn<SkatingRing, String> nameTableColumn;

    @FXML
    private TableColumn<SkatingRing, String> temperatureTableColumn;

    @FXML
    private TableColumn<SkatingRing, String> dateTableColumn;

    @FXML
    private TableColumn<SkatingRing, String> isReservedTableColumn;


    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);



    public void initialize(){

        idTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SkatingRing,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SkatingRing,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getId().toString());
            }
        });

        nameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SkatingRing,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SkatingRing,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getName());
            }
        });

        temperatureTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SkatingRing,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SkatingRing,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getTemperature().toString());
            }
        });

        dateTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SkatingRing,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SkatingRing,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getDate());
            }
        });

        isReservedTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SkatingRing,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SkatingRing,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getReserved());
            }
        });


        List<SkatingRing> skatingRingList = DatabaseUtils.getAllSkatingRings();

        ObservableList observableSkatingRingList =  FXCollections.observableArrayList(skatingRingList);

        skatingRingTableView.setItems(observableSkatingRingList);

        SearchSkatingRingThread thread = new SearchSkatingRingThread(skatingRingTableView, textFieldSkatingRingName,textFieldTemperature);
        Thread starter = new Thread(thread);
        starter.start();

    }


    public static List<SkatingRing> filterSkatingRings(List<SkatingRing> skatingRingList, String skatingRingNameFilter, String temperatureFilter) {

        return skatingRingList.stream()
                .filter(skatingRing -> skatingRing.getName().toLowerCase().contains(skatingRingNameFilter.toLowerCase()))
                .filter(skatingRing -> skatingRing.getTemperature().toString().contains(temperatureFilter))
                .collect(Collectors.toList());


    }


    public static List<SkatingRing> filterAndSortByHumidity(List<SkatingRing> skatingRingList, String skatingRingNameFilter, String temperatureFilter) {
        Set<String> filteredNames = skatingRingList.stream()
                .filter(skatingRing -> skatingRing.getName().toLowerCase().contains(skatingRingNameFilter.toLowerCase()))
                .map(SkatingRing::getName)
                .collect(Collectors.toSet());

        Map<String, List<SkatingRing>> filteredSkatingRingsByName = skatingRingList.stream()
                .filter(skatingRing -> filteredNames.contains(skatingRing.getName()))
                .filter(skatingRing -> skatingRing.getTemperature().toString().contains(temperatureFilter))
                .collect(Collectors.groupingBy(SkatingRing::getName));

        return filteredSkatingRingsByName.values().stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparing(SkatingRing::getTemperature))
                .collect(Collectors.toList());
    }





    public void reserveById(){
        String username = AccountController.getUsername();

        String stringId = textFieldIdToReserve.getText();
        Optional<Long> idToEdit = Validate.validateId(stringId, "SKATING_RING");

        if(idToEdit.isEmpty()){
            return;
        }

        if(Alerts.showConfirmation("Are you sure you want to reserve/unreserve?").equals(false)){
            return;
        }

        try{
            DatabaseUtils.reserveColumn("SKATING_RING", username, Long.parseLong(stringId));
        }
        catch (NumberFormatException e) {
            System.out.println("Error parsing ID from textField: " + stringId);
            e.printStackTrace();
        }

        List<SkatingRing> skatingRingList = DatabaseUtils.getAllSkatingRings();

        ObservableList observableSkatingRingList =  FXCollections.observableArrayList(skatingRingList);

        skatingRingTableView.setItems(observableSkatingRingList);
    }

}
