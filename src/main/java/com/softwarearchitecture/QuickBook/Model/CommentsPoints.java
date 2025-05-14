package com.softwarearchitecture.QuickBook.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CommentsPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "hotel_id", nullable = false)
    private int hotel_id;

    @Column(name = "user_id", nullable = false)
    private int user_id;

    @Column(name = "hotel_point", nullable = false)
    private BigDecimal hotel_point;

    @Column(name = "comments", nullable = false)
    private String comments;
}
