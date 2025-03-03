package com.gocile.shikesystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Information {
    private int grade;
    private String college;
    private String major;
    private String category;
    private LocalDateTime selectTime;
    private int maxQuantity;
}
