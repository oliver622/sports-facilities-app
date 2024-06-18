package hr.javafx.sports.sportsfacilitiesapp;

import hr.javafx.sports.alerts.Alerts;
import hr.javafx.sports.dates.PriorDateDisabler;
import hr.javafx.sports.threads.AddFacilityThread;
import hr.javafx.sports.validate.Validate;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class AddStadiumController {

    @FXML
    private TextField textFieldStadiumName;

    @FXML
    private TextField textFieldAudienceCapacity;

    @FXML
    private DatePicker datePicker;



    @FXML
    public void initialize(){
        PriorDateDisabler.DateDisablePriorToToday(datePicker);
    }


    public void stadiumSearch(){
        String stadiumName = textFieldStadiumName.getText().trim();

        if (stadiumName.isEmpty()) {
            Alerts.showAlert("Invalid Name", "Name cannot be empty.");
            return;
        }

        Optional<BigDecimal> capacity = Validate.validateBigDecimal(textFieldAudienceCapacity,"Audience capacity");

        if(capacity.isEmpty()){
            return;
        }

        if(!(Validate.validateDate(datePicker))){
            return;
        }
        LocalDate date = datePicker.getValue();
        String dateString = date.toString();

        if(Alerts.showConfirmation("Are you sure you want to add stadium?").equals(false)){
            return;
        }

        AddFacilityThread addThread = new AddFacilityThread("STADIUM", stadiumName, "AUDIENCE_CAPACITY", capacity.get(), dateString);
        new Thread(addThread).start();

        Alerts.showAlertToChangelog("Successfully added stadium", "Stadium " + stadiumName + " successfully added");
    }

}
