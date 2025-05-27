package com.softwarearchitecture.QuickBook.Controller;


import com.softwarearchitecture.QuickBook.Dto.CommentDto;
import com.softwarearchitecture.QuickBook.Repository.CommentRepository;
import com.softwarearchitecture.QuickBook.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentRepository commentRepository;
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    //CREATE COMMENT
    @PostMapping("/create")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        CommentDto savedComment = commentService.createComment(commentDto);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") long id)  {
        CommentDto comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<CommentDto>>getCommentByHotelId(@PathVariable("hotelId") long hotelId)  {
        List<CommentDto> comments = commentService.getCommentByHotelId(hotelId);
        return ResponseEntity.ok(comments);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDto>> getCommentsByUserId(@PathVariable long userId) {
        List<CommentDto> comments = commentService.getCommentByUserId(userId);
        return ResponseEntity.ok(comments);
    }
}
