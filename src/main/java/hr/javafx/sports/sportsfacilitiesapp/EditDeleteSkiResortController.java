package hr.javafx.sports.sportsfacilitiesapp;

import hr.javafx.sports.alerts.Alerts;
import hr.javafx.sports.dates.PriorDateDisabler;
import hr.javafx.sports.domain.GenericOne;
import hr.javafx.sports.domain.GenericTwo;
import hr.javafx.sports.domain.SkatingRing;
import hr.javafx.sports.domain.SkiResort;
import hr.javafx.sports.exceptions.EmptyNameException;
import hr.javafx.sports.threads.DeleteFacilityThread;
import hr.javafx.sports.threads.EditFacilityEntityThread;
import hr.javafx.sports.utils.DatabaseUtils;
import hr.javafx.sports.validate.Validate;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EditDeleteSkiResortController {


    @FXML
    private TextField idTextField;

    @FXML
    private TextField textFieldSkiResortName;

    @FXML
    private TextField textFieldHumidity;


    @FXML
    private DatePicker datePicker;



    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);



    @FXML
    public void initialize(){
        PriorDateDisabler.DateDisablePriorToToday(datePicker);
    }


    public void deleteSkiResortById() {
        String stringId = idTextField.getText();
        Optional<Long> idToDelete = Validate.validateId(stringId, "SKI_RESORT");

        if(idToDelete.isEmpty()){
            return;
        }

        if(Alerts.showConfirmation("Are you sure you want delete?").equals(false)){
            return;
        }

        DeleteFacilityThread deleteThread = new DeleteFacilityThread("SKI_RESORT", (long) Math.toIntExact(idToDelete.get()));
        new Thread(deleteThread).start();



        GenericOne<String> generic = new GenericOne<>(stringId);
        String genericStringId = generic.getValue();

        Alerts.showAlertToChangelog("Succcessful deletion", "Ski resort with ID " + genericStringId + " successfully deleted");

    }


    public void editSkiResortName() {

        String stringId = idTextField.getText();
        Optional<Long> idToEdit = Validate.validateId(stringId, "SKI_RESORT");

        if(idToEdit.isEmpty()){
            return;
        }

        String skiResortName = textFieldSkiResortName.getText().trim();

        try{
            checkEmptyText(skiResortName);
        }
        catch(EmptyNameException e){
            Alerts.showAlert("Invalid Name", "Name cannot be empty.");

            String message = "Invalid name, name cannot be empty.";
            logger.error(message, e);
            System.out.println(message);

            return;
        }

        SkiResort previousSkiResort = getSkiResortById(stringId);
        String previousName = previousSkiResort.getName();


        if(Alerts.showConfirmation("Are you sure you want to rename?").equals(false)){
            return;
        }



        GenericTwo<String, Optional<Long>> generic = new GenericTwo<>(stringId, idToEdit);
        String genericStringId = generic.getFirstValue();
        Optional<Long> genericId = generic.getSecondValue();

        EditFacilityEntityThread deleteThread = new EditFacilityEntityThread(genericId.get(), "SKI_RESORT", "NAME", skiResortName);
        new Thread(deleteThread).start();

        Alerts.showAlertToChangelog("Successful name change", "Ski resort successfully renamed to " + skiResortName + " from " + previousName + " on ID " + genericStringId);

    }

    public static SkiResort getSkiResortById(String stringId) {
        List<SkiResort> allSkiResorts = DatabaseUtils.getAllSkiResorts();
        return allSkiResorts.stream()
                .filter(skiResort -> skiResort.getId().equals(Long.parseLong(stringId))).findFirst().get();
    }

    public static void checkEmptyText(String skiResortName) throws EmptyNameException {
        if (skiResortName.isEmpty()) {
            throw new EmptyNameException("Invalid name, name cannot be empty.");
        }
    }

    public void editSkiResortHumidity(){

        String stringId = idTextField.getText();
        Optional<Long> idToEdit = Validate.validateId(stringId, "SKI_RESORT");

        if(idToEdit.isEmpty()){
            return;
        }

        Optional<BigDecimal> humidity = Validate.validateBigDecimal(textFieldHumidity,"Humidity");

        if(humidity.isEmpty()){
            return;
        }

        if(Alerts.showConfirmation("Are you sure you want to edit humidity?").equals(false)){
            return;
        }

        SkiResort previousSkiResort = getSkiResortById(stringId);
        String previousValue = previousSkiResort.getHumidity().toString();

        EditFacilityEntityThread deleteThread = new EditFacilityEntityThread(idToEdit.get(), "SKI_RESORT", "HUMIDITY", humidity.get());
        new Thread(deleteThread).start();

        Alerts.showAlertToChangelog("Successful humidity change", "Humidity successfully changed to " + humidity.get() + " from " + previousValue + " on ID " + stringId);

    }


    public void editSkiResortDate(){

        String stringId = idTextField.getText();
        Optional<Long> idToEdit = Validate.validateId(stringId, "SKI_RESORT");

        if(idToEdit.isEmpty()){
            return;
        }


        if(!(Validate.validateDate(datePicker))){
            return;
        }
        LocalDate date = datePicker.getValue();
        String dateString = date.toString();

        if(Alerts.showConfirmation("Are you sure you want to edit the date?").equals(false)){
            return;
        }

        SkiResort previousSkiResort = getSkiResortById(stringId);
        String previousDate = previousSkiResort.getDate().toString();


        EditFacilityEntityThread deleteThread = new EditFacilityEntityThread(idToEdit.get(), "SKI_RESORT", "DATE", dateString);
        new Thread(deleteThread).start();

        Alerts.showAlertToChangelog("Successful date change", "Date successfully changed to " + dateString + " from " + previousDate + " on ID " + stringId);

    }


    public void getId(){

    }

    public void setName(){

    }

    public void setHumidity(){

    }

    public void setDate(){

    }

    public void delete(){

    }


}
