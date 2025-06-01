package com.softwarearchitecture.QuickBook.Controller;


import com.softwarearchitecture.QuickBook.Dto.CommentDto;
import com.softwarearchitecture.QuickBook.Model.Comment;
import com.softwarearchitecture.QuickBook.Model.Hotel;
import com.softwarearchitecture.QuickBook.Model.User;
import com.softwarearchitecture.QuickBook.Repository.HotelRepository;
import com.softwarearchitecture.QuickBook.Repository.UserRepository;
import com.softwarearchitecture.QuickBook.Dto.UserDto;
import com.softwarearchitecture.QuickBook.Mapper.CommentMapper;
import com.softwarearchitecture.QuickBook.Service.CommentService;
import com.softwarearchitecture.QuickBook.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CommentController {
    
    private final CommentService commentService;
    private final UserService userService;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentController(CommentService commentService,
                             UserService userService,
                             HotelRepository hotelRepository,
                             UserRepository userRepository){
        this.commentService = commentService;
        this.userService = userService;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/add-new-comment")
public ResponseEntity<CommentDto> addComment(@RequestBody Map<String, String> payload) {
    try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userMail = authentication.getName();
        System.out.println("Authenticated user email: " + userMail);

        UserDto user = userService.getUserByMail(userMail);
        if (user == null) {
            System.out.println("User not found for email: " + userMail);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String rawHotelId = payload.get("hotelId");
        if (rawHotelId == null || rawHotelId.trim().isEmpty()) {
            System.out.println("hotelId is missing or empty from payload");
            return ResponseEntity.badRequest().build();
        }

        long hotelId;
        try {
            hotelId = Long.parseLong(rawHotelId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid hotelId format: " + rawHotelId);
            return ResponseEntity.badRequest().build();
        }

        String user_comment = payload.get("user_comment");
        if (user_comment == null || user_comment.trim().isEmpty()) {
            System.out.println("Comment is missing or empty");
            return ResponseEntity.badRequest().build();
        }

        Hotel hotel = hotelRepository.findById(hotelId)
                        .orElseThrow(() -> new RuntimeException("Hotel not found with id " + hotelId));
        User commentUser = userRepository.findById(user.getId())
                        .orElseThrow(() -> new RuntimeException("User not found with id " + user.getId()));

        Comment commentEntity = new Comment();
        commentEntity.setHotel(hotel);
        commentEntity.setUser(commentUser);
        commentEntity.setUser_comment(user_comment);

        Comment savedComment = commentService.createComment(commentEntity);

        // DTO'ya çevir ve JSON olarak dön
        CommentDto responseDto = new CommentDto();
        responseDto.setId(savedComment.getId());
        responseDto.setUser_comment(savedComment.getUser_comment());
        responseDto.setHotel_id(savedComment.getHotel().getId());
        responseDto.setUser_id(savedComment.getUser().getId());

        return ResponseEntity.ok(responseDto);

    } catch (Exception e) {
        System.out.println("Exception caught in /add-new-comment:");
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}


    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") long id)  {
        CommentDto comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDto>> getCommentsByUserId(@PathVariable long userId) {
        List<CommentDto> comments = commentService.getCommentByUserId(userId);
        return ResponseEntity.ok(comments);
    }
}
