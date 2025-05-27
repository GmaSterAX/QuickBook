package com.softwarearchitecture.QuickBook.Dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String mail;
    private String password;
    private String token; //mail doğrulamada kullanılacak.
}
