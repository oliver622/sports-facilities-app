package hr.javafx.sports.sportsfacilitiesapp;

import hr.javafx.sports.alerts.Alerts;
import hr.javafx.sports.dates.PriorDateDisabler;
import hr.javafx.sports.domain.GenericOne;
import hr.javafx.sports.domain.GenericTwo;
import hr.javafx.sports.domain.SkiResort;
import hr.javafx.sports.domain.Stadium;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EditDeleteStadiumController {


    @FXML
    private TextField idTextField;

    @FXML
    private TextField textFieldStadiumName;

    @FXML
    private TextField textFieldCapacity;


    @FXML
    private DatePicker datePicker;



    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);



    @FXML
    public void initialize(){
        PriorDateDisabler.DateDisablePriorToToday(datePicker);
    }


    public void deleteStadiumById() {
        String stringId = idTextField.getText();
        Optional<Long> idToDelete = Validate.validateId(stringId, "STADIUM");

        if(idToDelete.isEmpty()){
            return;
        }

        if(Alerts.showConfirmation("Are you sure you want delete?").equals(false)){
            return;
        }

        DeleteFacilityThread deleteThread = new DeleteFacilityThread("STADIUM", (long) Math.toIntExact(idToDelete.get()));
        new Thread(deleteThread).start();



        GenericOne<String> generic = new GenericOne<>(stringId);
        String genericStringId = generic.getValue();

        Alerts.showAlertToChangelog("Succcessful deletion", "Stadium with ID " + genericStringId + " successfully deleted");

    }


    public void editStadiumName() {

        String stringId = idTextField.getText();
        Optional<Long> idToEdit = Validate.validateId(stringId, "STADIUM");

        if(idToEdit.isEmpty()){
            return;
        }

        String stadiumName = textFieldStadiumName.getText().trim();

        try{
            checkEmptyText(stadiumName);
        }
        catch(EmptyNameException e){
            Alerts.showAlert("Invalid Name", "Name cannot be empty.");

            String message = "Invalid name, name cannot be empty.";
            logger.error(message, e);
            System.out.println(message);

            return;
        }

        Stadium previousStadium = getStadiumById(stringId);
        String previousName = previousStadium.getName();


        if(Alerts.showConfirmation("Are you sure you want to rename?").equals(false)){
            return;
        }



        GenericTwo<String, Optional<Long>> generic = new GenericTwo<>(stringId, idToEdit);
        String genericStringId = generic.getFirstValue();
        Optional<Long> genericId = generic.getSecondValue();

        EditFacilityEntityThread deleteThread = new EditFacilityEntityThread(genericId.get(), "STADIUM", "NAME", stadiumName);
        new Thread(deleteThread).start();

        Alerts.showAlertToChangelog("Successful name change", "Stadium successfully renamed to " + stadiumName + " from " + previousName + " on ID " + genericStringId);

    }

    public static Stadium getStadiumById(String stringId) {
        List<Stadium> allStadiums = DatabaseUtils.getAllStadiums();
        return allStadiums.stream()
                .filter(stadium -> stadium.getId().equals(Long.parseLong(stringId))).findFirst().get();
    }

    public static void checkEmptyText(String stadiumName) throws EmptyNameException {
        if (stadiumName.isEmpty()) {
            throw new EmptyNameException("Invalid name, name cannot be empty.");
        }
    }

    public void editStadiumCapacity(){

        String stringId = idTextField.getText();
        Optional<Long> idToEdit = Validate.validateId(stringId, "STADIUM");

        if(idToEdit.isEmpty()){
            return;
        }

        Optional<BigDecimal> capacity = Validate.validateBigDecimal(textFieldCapacity,"Audience capacity");

        if(capacity.isEmpty()){
            return;
        }

        if(Alerts.showConfirmation("Are you sure you want to edit audience capacity?").equals(false)){
            return;
        }

        Stadium previousStadium = getStadiumById(stringId);
        String previousValue = previousStadium.getAudienceCapacity().toString();

        EditFacilityEntityThread deleteThread = new EditFacilityEntityThread(idToEdit.get(), "STADIUM", "AUDIENCE_CAPACITY", capacity.get());
        new Thread(deleteThread).start();

        Alerts.showAlertToChangelog("Successful capacity change", "Audience capacity successfully changed to " + capacity.get() + " from " + previousValue + " on ID " + stringId);

    }


    public void editStadiumDate(){

        String stringId = idTextField.getText();
        Optional<Long> idToEdit = Validate.validateId(stringId, "STADIUM");

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

        Stadium previousStadium = getStadiumById(stringId);
        String previousDate = previousStadium.getDate().toString();


        EditFacilityEntityThread deleteThread = new EditFacilityEntityThread(idToEdit.get(), "STADIUM", "DATE", dateString);
        new Thread(deleteThread).start();

        Alerts.showAlertToChangelog("Successful date change", "Date successfully changed to " + dateString + " from " + previousDate + " on ID " + stringId);

    }

    public void getId(){

    }

    public void setName(){

    }

    public void setCapacity(){

    }

    public void setDate(){

    }

    public void delete(){

    }



}
