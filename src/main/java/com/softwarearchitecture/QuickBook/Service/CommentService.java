package com.softwarearchitecture.QuickBook.Service;

import com.softwarearchitecture.QuickBook.Model.Comment;
import com.softwarearchitecture.QuickBook.Dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto getCommentById(long id);
    List<CommentDto> getCommentByHotelId(long hotelId);
    List<CommentDto> getCommentByUserId(long userId);
    Comment createComment(Comment comment);



}

