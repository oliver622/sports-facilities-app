package hr.javafx.sports.fileutils;

import java.security.NoSuchAlgorithmException;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class AccountFileUtils {

    public static String hashPassword(String password){

        return BCrypt.withDefaults().hashToString(12, password.toCharArray());

    }




}
