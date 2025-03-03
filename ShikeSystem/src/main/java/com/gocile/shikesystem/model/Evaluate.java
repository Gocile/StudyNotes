package com.gocile.shikesystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluate {
    private String content;
    private int audited;
    private int passed;
}
