package com.fly.house.core.dto;

/**
 * Created by dimon on 1/31/14.
 */
public class AccountDto {

    private Long id;

    private String login;

    private String password;

    private String email;

    public AccountDto(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }


    public String getLogin() {
        return login;
    }


    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
