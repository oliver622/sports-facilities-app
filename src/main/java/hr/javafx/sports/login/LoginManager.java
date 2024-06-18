package hr.javafx.sports.login;

import at.favre.lib.crypto.bcrypt.BCrypt;
import hr.javafx.sports.utils.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class LoginManager{

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);

    public static boolean authenticateUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("login/users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                String storedUsername = userData[0].trim();
                String storedPassword = userData[1].trim();

                if (username.equals(storedUsername) && verifyPassword(password,storedPassword)) {
                    return true;
                }
            }
        } catch (IOException e) {
            String message = "Error with authenticating user.";
            logger.error(message, e);
            System.out.println(message);
        }
        return false;
    }

    public static boolean verifyPassword(String inputPassword, String hashedPassword){
        return BCrypt.verifyer().verify(inputPassword.toCharArray(), hashedPassword).verified;
    }

    public static boolean isAdmin(String username, String password) {

        try (BufferedReader reader = new BufferedReader(new FileReader("login/users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                String storedUsername = userData[0].trim();
                String storedPassword = userData[1].trim();
                String storedRole = userData[2].trim();

                if (username.equals(storedUsername) && verifyPassword(password,storedPassword)) {
                    if(storedRole.equals("admin")){
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            String message = "Error with checking admin.";
            logger.error(message, e);
            System.out.println(message);
        }
        return false;
    }
}

