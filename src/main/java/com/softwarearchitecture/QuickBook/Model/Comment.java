package com.softwarearchitecture.QuickBook.Model;


import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String user_comment;
    private Date created_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public Comment(long id, String userComment, Date created_at) {
        this.id = id;
        this.user_comment = userComment;
        this.created_at = created_at;
    }

    @PrePersist
    protected void onCreate(){
        this.created_at = new Date(); // tarihi atamak için
    }
}
