package hr.javafx.sports.account;

public class AccountController {

    protected static String username;

    protected static String password;

    protected static Boolean role;

    public AccountController(){}

    public AccountController(String username, String password, Boolean role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static Boolean getRole() {
        return role;
    }

}
