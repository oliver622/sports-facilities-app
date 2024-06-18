package hr.javafx.sports.sportsfacilitiesapp;

import hr.javafx.sports.account.AccountController;
import hr.javafx.sports.alerts.Alerts;
import hr.javafx.sports.domain.SkatingRing;
import hr.javafx.sports.domain.SkiResort;
import hr.javafx.sports.threads.SearchSkiResortThread;
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

public class SkiResortReserveController {


    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);

    @FXML
    private TextField textFieldSkiResortName;

    @FXML
    private TextField textFieldHumidity;

    @FXML
    private TextField textFieldIdToReserve;

    @FXML
    private TableView<SkiResort> skiResortTableView;

    @FXML
    private TableColumn<SkiResort, String> idTableColumn;

    @FXML
    private TableColumn<SkiResort, String> nameTableColumn;

    @FXML
    private TableColumn<SkiResort, String> humidityTableColumn;

    @FXML
    private TableColumn<SkiResort, String> dateTableColumn;

    @FXML
    private TableColumn<SkiResort, String> isReservedTableColumn;


    public void initialize(){

        idTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SkiResort,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SkiResort,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getId().toString());
            }
        });

        nameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SkiResort,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SkiResort,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getName());
            }
        });

        humidityTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SkiResort,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SkiResort,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getHumidity().toString());
            }
        });

        dateTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SkiResort,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SkiResort,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getDate());
            }
        });

        isReservedTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SkiResort,String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SkiResort,String> param){
                return new ReadOnlyStringWrapper(param.getValue().getReserved());
            }
        });


        List<SkiResort> skiResortList = DatabaseUtils.getAllSkiResorts();

        ObservableList observableSkiResortList =  FXCollections.observableArrayList(skiResortList);

        skiResortTableView.setItems(observableSkiResortList);

        SearchSkiResortThread thread = new SearchSkiResortThread(skiResortTableView, textFieldSkiResortName,textFieldHumidity);
        Thread starter = new Thread(thread);
        starter.start();


    }




    public static List<SkiResort> filterSkiResorts(List<SkiResort> skiResortList, String skiResortNameFilter, String humidityFilter) {

        return skiResortList.stream()
                .filter(skiResort -> skiResort.getName().toLowerCase().contains(skiResortNameFilter.toLowerCase()))
                .filter(skiResort -> skiResort.getHumidity().toString().contains(humidityFilter))
                .collect(Collectors.toList());


    }


    public static List<SkiResort> filterAndSortByHumidity(List<SkiResort> skiResortList, String skiResortNameFilter, String humidityFilter) {
        Set<String> filteredNames = skiResortList.stream()
                .filter(skatingRing -> skatingRing.getName().toLowerCase().contains(skiResortNameFilter.toLowerCase()))
                .map(SkiResort::getName)
                .collect(Collectors.toSet());

        Map<String, List<SkiResort>> filteredSkiResortsByName = skiResortList.stream()
                .filter(skiresort -> filteredNames.contains(skiresort.getName()))
                .filter(skiresort -> skiresort.getHumidity().toString().contains(humidityFilter))
                .collect(Collectors.groupingBy(SkiResort::getName));

        return filteredSkiResortsByName.values().stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparing(SkiResort::getHumidity))
                .collect(Collectors.toList());
    }






    public void reserveById(){
        String username = AccountController.getUsername();

        String stringId = textFieldIdToReserve.getText();
        Optional<Long> idToEdit = Validate.validateId(stringId, "SKI_RESORT");

        if(idToEdit.isEmpty()){
            return;
        }

        if(Alerts.showConfirmation("Are you sure you want to reserve/unreserve?").equals(false)){
            return;
        }

        try{
            DatabaseUtils.reserveColumn("SKI_RESORT", username, Long.parseLong(stringId));
        }
        catch (NumberFormatException e) {
            System.out.println("Error parsing ID from textField: " + stringId);
            e.printStackTrace();
        }

        List<SkiResort> skiResortList = DatabaseUtils.getAllSkiResorts();

        ObservableList observableSkiResortList =  FXCollections.observableArrayList(skiResortList);

        skiResortTableView.setItems(observableSkiResortList);
    }

}
