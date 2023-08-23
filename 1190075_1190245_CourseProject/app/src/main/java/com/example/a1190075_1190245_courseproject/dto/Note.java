package com.example.a1190075_1190245_courseproject.dto;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class Note {
    private String id = UUID.randomUUID().toString();
    private String text;
    private Date createdOn = new Date();
}
