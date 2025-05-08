package com.sud.userservice.models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends BaseModel{
    private Long id;
    private String name;
    private String email;
    private String hashedPassword;
}
