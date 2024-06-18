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

public class AddSkiResortController {

    @FXML
    private TextField textFieldSkiResortName;

    @FXML
    private TextField textFieldHumidity;

    @FXML
    public DatePicker datePicker;


    @FXML
    public void initialize(){
        PriorDateDisabler.DateDisablePriorToToday(datePicker);
    }

    public void skiResortSearch(){
        String skiResortName = textFieldSkiResortName.getText().trim();

        if (skiResortName.isEmpty()) {
            Alerts.showAlert("Invalid Name", "Name cannot be empty.");
            return;
        }

        Optional<BigDecimal> humidity = Validate.validateBigDecimal(textFieldHumidity,"Humidity");

        if(humidity.isEmpty()){
            return;
        }

        if(!(Validate.validateDate(datePicker))){
            return;
        }
        LocalDate date = datePicker.getValue();
        String dateString = date.toString();

        if(Alerts.showConfirmation("Are you sure you want to add ski resort?").equals(false)){
            return;
        }

        AddFacilityThread addThread = new AddFacilityThread("SKI_RESORT", skiResortName, "HUMIDITY", humidity.get(), dateString);
        new Thread(addThread).start();

        Alerts.showAlertToChangelog("Successfully added ski resort", "Ski resort " + skiResortName + " successfully added");
    }

}
