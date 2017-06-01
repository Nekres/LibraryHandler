package com.desolation.library.model;

/**
 *
 * @author nekres
 */
public class User {
    private final String login;
    private final String password;
    private final String name;
    private final String surname;
    private String middle_name;
    private final String address;
    private final String phone;
    private final String birthday;

    public User(String login, String password, String name, String surname, String middle_name, String address, String phone, String birthday) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.middle_name = middle_name;
        this.address = address;
        this.phone = phone;
        this.birthday = birthday;
    }


    
    public String getPassword() {
        return password;
    }


    public String getLogin() {
        return login;
    }

    
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirthday() {
        return birthday;
    }
    
    
}
