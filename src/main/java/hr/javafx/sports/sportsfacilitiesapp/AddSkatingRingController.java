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

public class AddSkatingRingController {

    @FXML
    private TextField textFieldSkatingRingName;

    @FXML
    private TextField textFieldTemperature;

    @FXML
    private DatePicker datePicker;


    @FXML
    public void initialize(){
        PriorDateDisabler.DateDisablePriorToToday(datePicker);
    }

    public void skatingRingSearch(){
        String skatingRingName = textFieldSkatingRingName.getText().trim();

        if (skatingRingName.isEmpty()) {
            Alerts.showAlert("Invalid Name", "Name cannot be empty.");
            return;
        }

        Optional<BigDecimal> temperature = Validate.validateBigDecimal(textFieldTemperature,"Temperature");

        if(temperature.isEmpty()){
            return;
        }

        if(!(Validate.validateDate(datePicker))){
            return;
        }
        LocalDate date = datePicker.getValue();
        String dateString = date.toString();

        if(Alerts.showConfirmation("Are you sure you want to add skating ring?").equals(false)){
            return;
        }

        AddFacilityThread addThread = new AddFacilityThread("SKATING_RING", skatingRingName, "TEMPERATURE", temperature.get(), dateString);
        new Thread(addThread).start();

        Alerts.showAlertToChangelog("Successfully added skating ring", "Skating ring " + skatingRingName + " successfully added");
    }


}
