package hr.javafx.sports.sportsfacilitiesapp;

import hr.javafx.sports.alerts.Alerts;
import hr.javafx.sports.dates.PriorDateDisabler;
import hr.javafx.sports.threads.AddFacilityThread;
import hr.javafx.sports.threads.DeleteFacilityThread;
import hr.javafx.sports.utils.DatabaseUtils;
import hr.javafx.sports.validate.Validate;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class AddPoolController {

    @FXML
    private TextField textFieldPoolName;

    @FXML
    private TextField textFieldDepth;

    @FXML
    private DatePicker datePicker;


    @FXML
    public void initialize(){
        PriorDateDisabler.DateDisablePriorToToday(datePicker);
    }

    public void addPool() {
        String poolName = textFieldPoolName.getText().trim();

        if (poolName.isEmpty()) {
            Alerts.showAlert("Invalid Name", "Name cannot be empty.");
            return;
        }

        Optional<BigDecimal> depth = Validate.validateBigDecimal(textFieldDepth,"Depth");

        if(depth.isEmpty()){
            return;
        }

        if(!(Validate.validateDate(datePicker))){
            return;
        }
        LocalDate date = datePicker.getValue();
        String dateString = date.toString();

        if(Alerts.showConfirmation("Are you sure you want to add pool?").equals(false)){
            return;
        }

        AddFacilityThread addThread = new AddFacilityThread("POOL", poolName, "DEPTH", depth.get(), dateString);
        new Thread(addThread).start();

        Alerts.showAlertToChangelog("Successfully added pool", "Pool" + poolName + " successfully added");
    }

}
