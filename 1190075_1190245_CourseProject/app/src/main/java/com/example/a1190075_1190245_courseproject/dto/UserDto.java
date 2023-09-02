package com.example.a1190075_1190245_courseproject.dto;

import com.example.a1190075_1190245_courseproject.enums.Preference;

import java.util.UUID;


public class UserDto {

    private String id = UUID.randomUUID().toString();
    private String firstName;
    private String lastName;
    private String nickName;
    private String email;
    private String passwords;
    private Preference preference;

    public UserDto() {

    }

    public UserDto(String firstName, String lastName, String nickName, String email, String passwords, Preference preference) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.email = email;
        this.passwords = passwords;
        this.preference = preference;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }
}
