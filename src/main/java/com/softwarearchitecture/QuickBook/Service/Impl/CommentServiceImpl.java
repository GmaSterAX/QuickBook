package com.softwarearchitecture.QuickBook.Service.Impl;


import com.softwarearchitecture.QuickBook.Dto.CommentDto;
import com.softwarearchitecture.QuickBook.Mapper.CommentMapper;
import com.softwarearchitecture.QuickBook.Model.Comment;
import com.softwarearchitecture.QuickBook.Repository.CommentRepository;
import com.softwarearchitecture.QuickBook.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {this.commentRepository = commentRepository; }


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
    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = CommentMapper.mapToComment(commentDto);
        Comment savedComment = commentRepository.save(comment);

        return CommentMapper.mapToCommentDto(savedComment);
    }
}
