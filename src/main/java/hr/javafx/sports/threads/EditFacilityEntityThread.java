package hr.javafx.sports.threads;

public class EditFacilityEntityThread extends DatabaseThread implements Runnable {

    private Long id;

    private String tableName;

    private String entityColumnName;

    private Object entity;

    public EditFacilityEntityThread(Long id, String tableName, String entityColumnName, Object entity) {
        this.id = id;
        this.tableName = tableName;
        this.entityColumnName = entityColumnName;
        this.entity = entity;
    }

    @Override
    public void run(){
        super.editFacility(id,tableName,entityColumnName,entity);
    }
}
