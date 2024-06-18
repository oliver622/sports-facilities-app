package hr.javafx.sports.sportsfacilitiesapp;

import hr.javafx.sports.alerts.Alerts;
import hr.javafx.sports.dates.PriorDateDisabler;
import hr.javafx.sports.domain.GenericOne;
import hr.javafx.sports.domain.GenericTwo;
import hr.javafx.sports.domain.Pool;
import hr.javafx.sports.domain.SkatingRing;
import hr.javafx.sports.exceptions.EmptyNameException;
import hr.javafx.sports.threads.DeleteFacilityThread;
import hr.javafx.sports.threads.EditFacilityEntityThread;
import hr.javafx.sports.utils.DatabaseUtils;
import hr.javafx.sports.validate.Validate;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EditDeleteSkatingRingController {

    @FXML
    private TextField idTextField;

    @FXML
    private TextField textFieldSkatingRingName;

    @FXML
    private TextField textFieldTemperature;


    @FXML
    private DatePicker datePicker;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);


    @FXML
    public void initialize(){
        PriorDateDisabler.DateDisablePriorToToday(datePicker);
    }



    public void deleteSkatingRingById() {
        String stringId = idTextField.getText();
        Optional<Long> idToDelete = Validate.validateId(stringId, "SKATING_RING");

        if(idToDelete.isEmpty()){
            return;
        }

        if(Alerts.showConfirmation("Are you sure you want delete?").equals(false)){
            return;
        }

        DeleteFacilityThread deleteThread = new DeleteFacilityThread("SKATING_RING", (long) Math.toIntExact(idToDelete.get()));
        new Thread(deleteThread).start();



        GenericOne<String> generic = new GenericOne<>(stringId);
        String genericStringId = generic.getValue();

        Alerts.showAlertToChangelog("Succcessful deletion", "Skating ring with ID " + genericStringId + " successfully deleted");

    }


    public void editSkatingRingName() {

        String stringId = idTextField.getText();
        Optional<Long> idToEdit = Validate.validateId(stringId, "SKATING_RING");

        if(idToEdit.isEmpty()){
            return;
        }

        String skatingRingName = textFieldSkatingRingName.getText().trim();

        try{
            checkEmptyText(skatingRingName);
        }
        catch(EmptyNameException e){
            Alerts.showAlert("Invalid Name", "Name cannot be empty.");

            String message = "Invalid name, name cannot be empty.";
            logger.error(message, e);
            System.out.println(message);

            return;
        }

        SkatingRing previousSkatingRing = getSkatingRingById(stringId);
        String previousName = previousSkatingRing.getName();


        if(Alerts.showConfirmation("Are you sure you want to rename?").equals(false)){
            return;
        }



        GenericTwo<String, Optional<Long>> generic = new GenericTwo<>(stringId, idToEdit);
        String genericStringId = generic.getFirstValue();
        Optional<Long> genericId = generic.getSecondValue();

        EditFacilityEntityThread deleteThread = new EditFacilityEntityThread(genericId.get(), "SKATING_RING", "NAME", skatingRingName);
        new Thread(deleteThread).start();

        Alerts.showAlertToChangelog("Successful name change", "Skating ring successfully renamed to " + skatingRingName + " from " + previousName + " on ID " + genericStringId);

    }

    public static SkatingRing getSkatingRingById(String stringId) {
        List<SkatingRing> allSkatingRings = DatabaseUtils.getAllSkatingRings();
        return allSkatingRings.stream()
                .filter(skatingRing -> skatingRing.getId().equals(Long.parseLong(stringId))).findFirst().get();
    }

    public static void checkEmptyText(String skatingRingName) throws EmptyNameException {
        if (skatingRingName.isEmpty()) {
            throw new EmptyNameException("Invalid name, name cannot be empty.");
        }
    }

    public void editSkatingRingTemperature(){

        String stringId = idTextField.getText();
        Optional<Long> idToEdit = Validate.validateId(stringId, "SKATING_RING");

        if(idToEdit.isEmpty()){
            return;
        }

        Optional<BigDecimal> temperature = Validate.validateBigDecimal(textFieldTemperature,"Temperature");

        if(temperature.isEmpty()){
            return;
        }

        if(Alerts.showConfirmation("Are you sure you want to edit temperature?").equals(false)){
            return;
        }

        SkatingRing previousSkatingRing = getSkatingRingById(stringId);
        String previousValue = previousSkatingRing.getTemperature().toString();

        EditFacilityEntityThread deleteThread = new EditFacilityEntityThread(idToEdit.get(), "SKATING_RING", "TEMPERATURE", temperature.get());
        new Thread(deleteThread).start();

        Alerts.showAlertToChangelog("Successful temperature change", "Temperature successfully changed to " + temperature.get() + " from " + previousValue + " on ID " + stringId);

    }


    public void editSkatingRingDate(){

        String stringId = idTextField.getText();
        Optional<Long> idToEdit = Validate.validateId(stringId, "SKATING_RING");

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

        SkatingRing previousSkatingRing = getSkatingRingById(stringId);
        String previousDate = previousSkatingRing.getDate().toString();


        EditFacilityEntityThread deleteThread = new EditFacilityEntityThread(idToEdit.get(), "SKATING_RING", "DATE", dateString);
        new Thread(deleteThread).start();

        Alerts.showAlertToChangelog("Successful date change", "Date successfully changed to " + dateString + " from " + previousDate + " on ID " + stringId);

    }







    public void getId(){

    }

    public void setName(){

    }

    public void setTemperature(){

    }

    public void setDate(){

    }

    public void delete(){

    }



}
