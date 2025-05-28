package com.softwarearchitecture.QuickBook.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

    private long id;
    private String message;
    private String messageTitle;
    private long user_id;

    public NotificationDto(String message, String messageTitle){
        this.message = message;
        this.messageTitle = messageTitle;
    }
}
