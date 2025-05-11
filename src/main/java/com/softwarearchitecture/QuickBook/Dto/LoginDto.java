package com.softwarearchitecture.QuickBook.Dto;

import lombok.Data;

@Data
public class LoginDto {
    private long id;
    private String name;
    private String mail;
    private String phone;
    private String password;
}
