package hr.javafx.sports.login;

import java.io.Serializable;

public class UserBase implements Serializable{

    private String username;

    private String password;

    private String role;

    public UserBase(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public static class Builder{

        private String username;

        private String password;

        private String role;

        public Builder(String username){
            this.username = username;
        }

        public Builder password(String password){
            this.password = password;
            return this;
        }

        public Builder role(String role){
            this.role = role;
            return this;
        }

        public UserBase build(){ return new UserBase(username, password, role); }

    }








}
