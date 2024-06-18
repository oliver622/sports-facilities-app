package hr.javafx.sports.dates;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class PriorDateDisabler {

    public static void DateDisablePriorToToday(DatePicker datePicker) {
        datePicker.getEditor().setDisable(true);
        datePicker.getEditor().setOpacity(1);
        datePicker.setFocusTraversable(false);

        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }
}
