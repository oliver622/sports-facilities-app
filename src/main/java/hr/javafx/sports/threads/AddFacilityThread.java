package hr.javafx.sports.threads;

import java.math.BigDecimal;

public class AddFacilityThread extends DatabaseThread implements Runnable{

    private String tableName;
    private String name;

    private String valueColumnName;
    private BigDecimal value;
    String date;

    public AddFacilityThread(String tableName, String name, String valueColumnName, BigDecimal value, String date) {
        this.tableName = tableName;
        this.name = name;
        this.value = value;
        this.date = date;
        this.valueColumnName = valueColumnName;
    }

    @Override
    public void run(){
        super.addFacility(tableName,name,valueColumnName,value,date);
    }
}
