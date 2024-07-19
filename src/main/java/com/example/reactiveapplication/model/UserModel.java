package com.example.reactiveapplication.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "users")
@Setter
@Getter
public class UserModel {

    @Id
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
}
