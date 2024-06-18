package hr.javafx.sports.threads;

import hr.javafx.sports.domain.Pool;
import hr.javafx.sports.domain.SkatingRing;
import hr.javafx.sports.domain.SkiResort;
import hr.javafx.sports.domain.Stadium;
import hr.javafx.sports.utils.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public abstract class DatabaseThread {

    public static Boolean isDatabaseOperationInProgress = false;


    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);
    public synchronized List<Pool> getAllPools() {

        while(isDatabaseOperationInProgress) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        isDatabaseOperationInProgress = true;

        List<Pool> poolList = DatabaseUtils.getAllPools();

        isDatabaseOperationInProgress = false;

        notifyAll();

        return poolList;
    }


    public synchronized List<SkatingRing> getAllSkatingRings() {

        while(isDatabaseOperationInProgress) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        isDatabaseOperationInProgress = true;

        List<SkatingRing> skatingRingList = DatabaseUtils.getAllSkatingRings();

        isDatabaseOperationInProgress = false;

        notifyAll();

        return skatingRingList;
    }



    public synchronized List<SkiResort> getAllSkiResorts() {

        while(isDatabaseOperationInProgress) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        isDatabaseOperationInProgress = true;

        List<SkiResort> skiResortList = DatabaseUtils.getAllSkiResorts();

        isDatabaseOperationInProgress = false;

        notifyAll();

        return skiResortList;
    }



    public synchronized List<Stadium> getAllStadiums() {

        while(isDatabaseOperationInProgress) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        isDatabaseOperationInProgress = true;

        List<Stadium> stadiumList = DatabaseUtils.getAllStadiums();

        isDatabaseOperationInProgress = false;

        notifyAll();

        return stadiumList;
    }






    public synchronized void deleteFacility(String name, Long id) {

        while(isDatabaseOperationInProgress) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        isDatabaseOperationInProgress = true;

        DatabaseUtils.deleteRecordById(name, Math.toIntExact(id));

        isDatabaseOperationInProgress = false;

        notifyAll();

    }




    public synchronized void addFacility(String tableName, String name, String valueColumnName, BigDecimal value, String date) {

        while(isDatabaseOperationInProgress) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        isDatabaseOperationInProgress = true;

        DatabaseUtils.addFacility(tableName,name,valueColumnName,value,date);

        isDatabaseOperationInProgress = false;

        notifyAll();

    }


    public synchronized void editFacility(Long id, String tableName, String entityColumnName, Object entity) {

        while(isDatabaseOperationInProgress) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        isDatabaseOperationInProgress = true;

        DatabaseUtils.editFacilityEntity(id, tableName, entityColumnName, entity);

        isDatabaseOperationInProgress = false;

        notifyAll();

    }





}
