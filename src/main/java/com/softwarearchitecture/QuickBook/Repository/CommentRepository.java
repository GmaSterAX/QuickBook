package com.softwarearchitecture.QuickBook.Repository;

import com.softwarearchitecture.QuickBook.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findById(long commentId);
    List<Comment> findCommentByHotelId(long hotelId);
    List<Comment> findCommentByUserId(long userId);
}
