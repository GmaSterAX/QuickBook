package com.softwarearchitecture.QuickBook.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;
    private String name;
    private String mail;
    private String phone;
    private String password;

}
