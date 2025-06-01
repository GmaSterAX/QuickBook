package com.softwarearchitecture.QuickBook.Dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private long id;
    private String user_comment;
    private Date created_at;
    private long user_id;
    private long hotel_id;
}
