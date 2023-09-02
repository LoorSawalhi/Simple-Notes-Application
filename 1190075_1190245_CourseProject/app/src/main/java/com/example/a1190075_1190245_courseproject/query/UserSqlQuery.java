package com.example.a1190075_1190245_courseproject.query;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSqlQuery {
    private List<String> id = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String nickName;
    private String email;
}
