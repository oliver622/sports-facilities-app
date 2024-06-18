package hr.javafx.sports.utils;

import hr.javafx.sports.alerts.Alerts;
import hr.javafx.sports.domain.Pool;
import hr.javafx.sports.domain.SkatingRing;
import hr.javafx.sports.domain.SkiResort;
import hr.javafx.sports.domain.Stadium;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Properties;

public class DatabaseUtils {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);
    private static final String DATABASE_FILE = "conf/database.properties";

    private static Connection connectToDatabase() throws SQLException, IOException {
        Properties svojstva = new Properties();
        svojstva.load(new FileReader(DATABASE_FILE));
        String urlBazePodataka = svojstva.getProperty("databaseUrl");
        String korisnickoIme = svojstva.getProperty("username");
        String lozinka = svojstva.getProperty("password");
        Connection veza = DriverManager.getConnection(urlBazePodataka,
                korisnickoIme, lozinka);
        return veza;
    }



    public static List<Pool> getAllPools(){
        List<Pool> poolList = new ArrayList<>();

        try (Connection connection = connectToDatabase()){
            String sqlQuery = "SELECT * FROM POOL";
            try (Statement statement = connection.createStatement()){
                ResultSet rs = statement.executeQuery(sqlQuery);

                while(rs.next()){
                    Long id = rs.getLong("ID");
                    String name = rs.getString("NAME");
                    BigDecimal depth = rs.getBigDecimal("DEPTH");
                    String date = rs.getString("DATE");
                    String reserved = rs.getString("RESERVED");

                    Pool pool = new Pool.Builder(id).name(name).depth(depth).date(date).reserved(reserved).build();
                    poolList.add(pool);
                }
            }


        }
        catch (SQLException | IOException e) {
            String message = "Error with reading pool from database";
            logger.error(message, e);
            System.out.println(message);
        }

        return poolList;

    }



    public static List<SkatingRing> getAllSkatingRings(){
        List<SkatingRing> skatingRingList = new ArrayList<>();

        try (Connection connection = connectToDatabase()){
            String sqlQuery = "SELECT * FROM SKATING_RING";
            try (Statement statement = connection.createStatement()){
                ResultSet rs = statement.executeQuery(sqlQuery);

                while(rs.next()){
                    Long id = rs.getLong("ID");
                    String name = rs.getString("NAME");
                    BigDecimal temperature = rs.getBigDecimal("TEMPERATURE");
                    String date = rs.getString("DATE");
                    String reserved = rs.getString("RESERVED");

                    SkatingRing skatingRing = new SkatingRing.Builder(id).name(name).temperature(temperature).date(date).reserved(reserved).build();
                    skatingRingList.add(skatingRing);
                }
            }


        }
        catch (SQLException | IOException e) {
            String message = "Error with reading skating ring from database";
            logger.error(message, e);
            System.out.println(message);
        }

        return skatingRingList;

    }




    public static List<SkiResort> getAllSkiResorts(){
        List<SkiResort> skiResortList = new ArrayList<>();

        try (Connection connection = connectToDatabase()){
            String sqlQuery = "SELECT * FROM SKI_RESORT";
            try (Statement statement = connection.createStatement()){
                ResultSet rs = statement.executeQuery(sqlQuery);

                while(rs.next()){
                    Long id = rs.getLong("ID");
                    String name = rs.getString("NAME");
                    BigDecimal humidity = rs.getBigDecimal("HUMIDITY");
                    String date = rs.getString("DATE");
                    String reserved = rs.getString("RESERVED");

                    SkiResort skiResort = new SkiResort.Builder(id).name(name).humidity(humidity).date(date).reserved(reserved).build();
                    skiResortList.add(skiResort);
                }
            }


        }
        catch (SQLException | IOException e) {
            String message = "Error with reading skating ring from database";
            logger.error(message, e);
            System.out.println(message);
        }

        return skiResortList;

    }





    public static List<Stadium> getAllStadiums(){
        List<Stadium> stadiumList = new ArrayList<>();

        try (Connection connection = connectToDatabase()){
            String sqlQuery = "SELECT * FROM STADIUM";
            try (Statement statement = connection.createStatement()){
                ResultSet rs = statement.executeQuery(sqlQuery);

                while(rs.next()){
                    Long id = rs.getLong("ID");
                    String name = rs.getString("NAME");
                    BigDecimal audienceCapacity = rs.getBigDecimal("AUDIENCE_CAPACITY");
                    String date = rs.getString("DATE");
                    String reserved = rs.getString("RESERVED");

                    Stadium stadium = new Stadium.Builder(id).name(name).audienceCapacity(audienceCapacity).date(date).reserved(reserved).build();
                    stadiumList.add(stadium);
                }
            }


        }
        catch (SQLException | IOException e) {
            String message = "Error with reading skating ring from database";
            logger.error(message, e);
            System.out.println(message);
        }

        return stadiumList;

    }


    public static boolean doesRecordExist(String tableName, Long recordId) {
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE ID = ?";

        try (Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, recordId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }

        }
        catch (SQLException | IOException e) {
            String message = "Error with deleting " + tableName + " "+ recordId + " from database.";
            logger.error(message, e);
            System.out.println(message);
        }

        return false;
    }



    public static void deleteRecordById(String tableName, int recordId) {
        String deleteQuery = "DELETE FROM " + tableName + " WHERE ID = ?";

        try (Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, recordId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException | IOException e) {
                String message = "Error with deleting " + tableName + " "+ recordId + " from database.";
                logger.error(message, e);
                System.out.println(message);
            }
    }




    public static void reserveColumn(String tableName, String username, Long reserveId) {
        try (Connection connection = connectToDatabase()) {
            String checkQuery = "SELECT RESERVED FROM " + tableName + " WHERE ID = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setLong(1, reserveId);
                ResultSet resultSet = checkStatement.executeQuery();

                if (resultSet.next()) {
                    String currentReservation = resultSet.getString("RESERVED");

                    if ("Unreserved".equals(currentReservation)) {
                        String updateQuery = "UPDATE " + tableName + " SET RESERVED = ? WHERE ID = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setString(1, username);
                            updateStatement.setLong(2, reserveId);
                            int rowsAffected = updateStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                Alerts.showAlertToChangelog("Reservation successful", "Reservation successful for facility with ID " + reserveId);
                            } else {
                                Alerts.showAlert("Reservation unsuccessful", "Reservation unsuccessful for facility with ID " + reserveId);
                            }
                        }
                    } else if (username.equals(currentReservation)) {
                        String updateQuery = "UPDATE " + tableName + " SET RESERVED = 'Unreserved' WHERE ID = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setLong(1, reserveId);
                            int rowsAffected = updateStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                Alerts.showAlertToChangelog("Successful unreservation", "Successful unreservation of ID " + reserveId);
                            } else {
                                Alerts.showAlert("Unsuccessful reservation", "Unsuccessful unreservation of ID " + reserveId);
                            }
                        }
                    } else {
                        Alerts.showAlert("Reservation reserved by another user", "Reservation reserved by another user");
                    }
                } else {
                    Alerts.showAlert("ID not found", "ID not found");
                }
            }
        } catch (SQLException | IOException e) {
            String message = "Error updating RESERVED column in " + tableName;
            logger.error(message, e);
            System.out.println(message);
        }
    }



    public static void addFacility(String tableName, String name, String valueColumnName, BigDecimal value, String date) {
        String insertQuery = "INSERT INTO " + tableName + " (NAME, " + valueColumnName + ", DATE,RESERVED) VALUES (?, ?, ?, 'Unreserved')";

        try (Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, name);
            preparedStatement.setBigDecimal(2, value);
            preparedStatement.setString(3, date);

            preparedStatement.executeUpdate();

            System.out.println("Facility successfully added to the database.");

        }
        catch (SQLException | IOException e) {
            String message = "Error adding facility in " + tableName;
            logger.error(message, e);
            System.out.println(message);
        }
    }




    public static void editFacilityEntity(Long id, String tableName, String  entityColumnName, Object entity) {
        String updateQuery = "UPDATE " + tableName + " SET " + entityColumnName + " = ?  WHERE ID = ?";

        try (Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            if (entity instanceof String) {
                preparedStatement.setString(1, (String) entity);
            } else if (entity instanceof BigDecimal) {
                preparedStatement.setBigDecimal(1, (BigDecimal) entity);
            } else {
                System.out.println("Unsupported entity type");
            }

            preparedStatement.setLong(2, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Entity successfully updated.");
            } else {
                System.out.println("Facility not found.");
            }
        }
        catch (SQLException | IOException e) {
            String message = "Error with editing facility.";
            logger.error(message, e);
            System.out.println(message);
        }
    }


}
