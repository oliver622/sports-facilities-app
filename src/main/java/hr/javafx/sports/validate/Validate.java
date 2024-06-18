package hr.javafx.sports.validate;

import hr.javafx.sports.alerts.Alerts;
import hr.javafx.sports.exceptions.IDZeroOrSmallerException;
import hr.javafx.sports.exceptions.NoFacilityWithIDException;
import hr.javafx.sports.exceptions.ValueZeroOrSmallerException;
import hr.javafx.sports.utils.DatabaseUtils;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class Validate {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);

    public static Optional<Long> validateId(String stringId, String table) throws IDZeroOrSmallerException {


        Long idToEdit;

        try {
            idToEdit = Long.valueOf(stringId);
        } catch (NumberFormatException e) {
            Alerts.showAlert("Invalid ID", "ID must be a number");

            String message = "Invalid ID, must be a number.";
            logger.error(message, e);
            System.out.println(message);

            return Optional.empty();
        }

        try {
            isIdBiggerThanZero(idToEdit);
        }
        catch(IDZeroOrSmallerException e){
            Alerts.showAlert("Invalid ID", "ID must be greater than 0.");

            String message = "Invalid ID, must be greater than 0.";
            logger.error(message, e);
            System.out.println(message);

            return Optional.empty();
        }

        try{
            doesFacilityExist(idToEdit,table);
        }
        catch(NoFacilityWithIDException e){
            Alerts.showAlert("Invalid ID", "No facility associated with that ID.");

            String message = "Invalid ID, no facility associated with that ID.";
            logger.error(message, e);
            System.out.println(message);

            return Optional.empty();
        }

        return Optional.of(idToEdit);
    }

    public static void isIdBiggerThanZero(Long id){
        if(id <= 0) {
            throw new IDZeroOrSmallerException("ID must be greater than 0.");
        }
    }

    public static void doesFacilityExist(Long idToEdit, String table) throws NoFacilityWithIDException {
        if(!DatabaseUtils.doesRecordExist(table, idToEdit)){
            throw new NoFacilityWithIDException("No facility associated with that ID.");
        }
    }

    public static Optional<BigDecimal> validateBigDecimal(TextField valueTextField, String valueText){
        BigDecimal value;

        try {
            value = new BigDecimal(valueTextField.getText());
        } catch (NumberFormatException e) {
            Alerts.showAlert("Invalid " + valueText, "Please enter a valid numeric " + valueText + ".");

            String message = "Invalid " + valueText + ", must be numeric.";
            logger.error(message, e);
            System.out.println(message);

            return Optional.empty();
        }

        try{
            isValueGreaterThanZero(value,valueText);
        }
        catch(ValueZeroOrSmallerException e){
            Alerts.showAlert("Invalid " + valueText, valueText + " must be greater than 0.");

            String message = "Invalid " + valueText + ", must be greater than zero.";
            logger.error(message, e);
            System.out.println(message);

            return Optional.empty();
        }

        return Optional.of(value);
    }

    public static void isValueGreaterThanZero(BigDecimal value, String valueText) {
        if(value.compareTo(BigDecimal.ZERO) <= 0){
            throw new ValueZeroOrSmallerException("Invalid " + valueText + ", must be greater than  zero.");
        }
    }

    public static Boolean validateDate(DatePicker datePicker){
        LocalDate date;

        try {
            date = datePicker.getValue();
            String dateString = date.toString();
        } catch (NullPointerException e) {
            Alerts.showAlert("Invalid date", "Date cannot be empty.");

            String message = "Invalid date, date cannot be empty.";
            logger.error(message, e);
            System.out.println(message);

            return false;
        }
        return true;
    }


}
