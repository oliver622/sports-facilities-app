package hr.javafx.sports.threads;

public class DeleteFacilityThread extends DatabaseThread implements Runnable{

    private String name;

    private Long id;

    public DeleteFacilityThread(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public void run(){
        super.deleteFacility(name,id);
    }


}
