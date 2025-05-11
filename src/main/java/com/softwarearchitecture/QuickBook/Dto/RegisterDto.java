package com.softwarearchitecture.QuickBook.Dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String name;
    private String mail;
    private String phone;
    private String password;
}
