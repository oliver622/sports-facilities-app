package hr.javafx.sports.sportsfacilitiesapp;

import hr.javafx.sports.account.AccountController;
import hr.javafx.sports.dates.PriorDateDisabler;
import hr.javafx.sports.domain.GenericOne;
import hr.javafx.sports.domain.GenericTwo;
import hr.javafx.sports.domain.Pool;
import hr.javafx.sports.exceptions.EmptyNameException;
import hr.javafx.sports.threads.DeleteFacilityThread;
import hr.javafx.sports.threads.EditFacilityEntityThread;
import hr.javafx.sports.utils.DatabaseUtils;
import hr.javafx.sports.alerts.Alerts;
import hr.javafx.sports.validate.Validate;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JOptionPane;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class EditDeletePoolController {

    @FXML
    private TextField idTextField;

    @FXML
    private TextField textFieldPoolName;

    @FXML
    private TextField textFieldDepth;


    @FXML
    private DatePicker datePicker;


    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);












    public void getId(){

    }

    public void setName(){

    }

    public void setDepth(){

    }

    public void setDate(){

    }

    @FXML
    public void initialize(){
        PriorDateDisabler.DateDisablePriorToToday(datePicker);
    }



    public void deletePoolById() {
        String stringId = idTextField.getText();
        Optional<Long> idToDelete = Validate.validateId(stringId, "POOL");

        if(idToDelete.isEmpty()){
            return;
        }

        if(Alerts.showConfirmation("Are you sure you want delete?").equals(false)){
            return;
        }

        DeleteFacilityThread deleteThread = new DeleteFacilityThread("POOL", (long) Math.toIntExact(idToDelete.get()));
        new Thread(deleteThread).start();



        GenericOne<String> generic = new GenericOne<>(stringId);
        String genericStringId = generic.getValue();

        Alerts.showAlertToChangelog("Succcessful deletion", "Pool with ID " + genericStringId + " successfully deleted");

    }




        public void editPoolName() {

            String stringId = idTextField.getText();
            Optional<Long> idToEdit = Validate.validateId(stringId, "POOL");

            if(idToEdit.isEmpty()){
                return;
            }

            String poolName = textFieldPoolName.getText().trim();

            try{
                checkEmptyText(poolName);
            }
            catch(EmptyNameException e){
                Alerts.showAlert("Invalid Name", "Name cannot be empty.");

                String message = "Invalid name, name cannot be empty.";
                logger.error(message, e);
                System.out.println(message);

                return;
            }

            Pool previousPool = getPoolById(stringId);
            String previousName = previousPool.getName();


            if(Alerts.showConfirmation("Are you sure you want to rename?").equals(false)){
                return;
            }



            GenericTwo<String, Optional<Long>> generic = new GenericTwo<>(stringId, idToEdit);
            String genericStringId = generic.getFirstValue();
            Optional<Long> genericId = generic.getSecondValue();

            EditFacilityEntityThread deleteThread = new EditFacilityEntityThread(genericId.get(), "POOL", "NAME", poolName);
            new Thread(deleteThread).start();

            Alerts.showAlertToChangelog("Successful name change", "Pool successfully renamed to " + poolName + " from " + previousName + " on ID " + genericStringId);

        }

    public static Pool getPoolById(String stringId) {
        List<Pool> allPools = DatabaseUtils.getAllPools();
        return allPools.stream()
                .filter(pool -> pool.getId().equals(Long.parseLong(stringId))).findFirst().get();
    }

    public static void checkEmptyText(String poolName) throws EmptyNameException {
        if (poolName.isEmpty()) {
            throw new EmptyNameException("Invalid name, name cannot be empty.");
        }
    }

        public void editPoolDepth(){

            String stringId = idTextField.getText();
            Optional<Long> idToEdit = Validate.validateId(stringId, "POOL");

            if(idToEdit.isEmpty()){
                return;
            }

            Optional<BigDecimal> depth = Validate.validateBigDecimal(textFieldDepth,"Depth");

            if(depth.isEmpty()){
                return;
            }

            if(Alerts.showConfirmation("Are you sure you want to edit depth?").equals(false)){
                return;
            }

            Pool previousPool = getPoolById(stringId);
            String previousValue = previousPool.getDepth().toString();

            EditFacilityEntityThread deleteThread = new EditFacilityEntityThread(idToEdit.get(), "POOL", "DEPTH", depth.get());
            new Thread(deleteThread).start();

            Alerts.showAlertToChangelog("Successful depth change", "Depth successfully changed to " + depth.get() + " from " + previousValue + " on ID " + stringId);

        }


        public void editPoolDate(){

            String stringId = idTextField.getText();
            Optional<Long> idToEdit = Validate.validateId(stringId, "POOL");

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

            Pool previousPool = getPoolById(stringId);
            String previousDate = previousPool.getDate().toString();


            EditFacilityEntityThread deleteThread = new EditFacilityEntityThread(idToEdit.get(), "POOL", "DATE", dateString);
            new Thread(deleteThread).start();

            Alerts.showAlertToChangelog("Successful date change", "Date successfully changed to " + dateString + " from " + previousDate + " on ID " + stringId);

        }










}
