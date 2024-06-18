module hr.javafx.sports.sportsfacilitiesapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;
    requires bcrypt;
    requires java.desktop;


    opens hr.javafx.sports.sportsfacilitiesapp to javafx.fxml;
    exports hr.javafx.sports.sportsfacilitiesapp;
    exports hr.javafx.sports.account;
    opens hr.javafx.sports.account to javafx.fxml;
}