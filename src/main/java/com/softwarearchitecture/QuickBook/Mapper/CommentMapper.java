package com.softwarearchitecture.QuickBook.Mapper;

import com.softwarearchitecture.QuickBook.Dto.CommentDto;
import com.softwarearchitecture.QuickBook.Model.Comment;


public class CommentMapper {

    public static CommentDto mapToCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getUser_comment(),
                comment.getCreated_at(),
                comment.getUser().getId(),
                comment.getHotel().getId()
        );

    }
    public static Comment mapToComment(CommentDto commentDto){
        return new Comment(
                commentDto.getId(),
                commentDto.getUser_comment(),
                commentDto.getCreated_at()
        );


    }


}
