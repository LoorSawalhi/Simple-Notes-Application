package com.example.a1190075_1190245_courseproject.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class User {

    private String id = UUID.randomUUID().toString();
    private String firstName;
    private String lastName;
    private String email;
    private String passwords;
}
