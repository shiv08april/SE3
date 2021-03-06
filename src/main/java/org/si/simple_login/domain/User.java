package org.si.simple_login.domain;

/**
 * This domain class represents users, which correspond to entries of the user table.
 */
public class User {

    private String userName;
    private String email;
    private String password;

    private String role;

    public User(){}

    public User(String userName, String password){

        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String email, String password){

        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(String userName, String email, String password, String role){

        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role= role!=null?role:"USER";

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
