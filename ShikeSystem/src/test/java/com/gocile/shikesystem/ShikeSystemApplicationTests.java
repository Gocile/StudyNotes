package com.gocile.shikesystem;

import com.gocile.shikesystem.service.StuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShikeSystemApplicationTests {

    @Autowired
    private StuService stuService;

    @Test
    void contextLoads() {
        stuService.getCourseCategory("1");
    }

}
