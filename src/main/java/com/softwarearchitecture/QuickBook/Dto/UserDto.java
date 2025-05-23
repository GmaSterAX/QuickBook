package com.softwarearchitecture.QuickBook.Dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;
    private String name;
    private String mail;
    private String phone;
    private String password;

}
