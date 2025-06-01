package com.softwarearchitecture.QuickBook.Service.Impl;


import com.softwarearchitecture.QuickBook.Dto.CommentDto;
import com.softwarearchitecture.QuickBook.Mapper.CommentMapper;
import com.softwarearchitecture.QuickBook.Model.Comment;
import com.softwarearchitecture.QuickBook.Model.Hotel;
import com.softwarearchitecture.QuickBook.Model.User;
import com.softwarearchitecture.QuickBook.Repository.CommentRepository;
import com.softwarearchitecture.QuickBook.Repository.UserRepository;
import com.softwarearchitecture.QuickBook.Repository.HotelRepository;
import com.softwarearchitecture.QuickBook.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private HotelRepository hotelRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, HotelRepository hotelRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
    }


    @Override
    public CommentDto getCommentById(long id) {
        Comment comment = commentRepository.findById(id);
        return CommentMapper.mapToCommentDto(comment);
    }

    @Override
    public List<CommentDto> getCommentByHotelId(long hotelId) {
        List<Comment> comments = commentRepository.findCommentByHotelId(hotelId);
        return comments.stream()
                .map(CommentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getCommentByUserId(long userId) {
        List<Comment> comments = commentRepository.findCommentByUserId(userId);
        return comments.stream()
                .map(CommentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public Comment createComment(Comment comment) {
        User user = userRepository.findById(comment.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Hotel hotel = hotelRepository.findById(comment.getHotel().getId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        comment.setUser_comment(comment.getUser_comment());
        comment.setUser(user);
        comment.setHotel(hotel);

        Comment savedComment = commentRepository.save(comment);
        return savedComment;
    }
}
