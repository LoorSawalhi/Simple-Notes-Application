package com.example.a1190075_1190245_courseproject.query;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoteSqlQuery {
    private List<String> id = new ArrayList<>();
    private String title;
    private String content;

}
