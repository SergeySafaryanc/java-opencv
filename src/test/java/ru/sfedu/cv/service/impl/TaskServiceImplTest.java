package ru.sfedu.cv.service.impl;

import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sfedu.cv.service.TaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceImplTest {

    @Autowired
    TaskService taskService;

    @Test
    void isNotNullTest() {
        assertNotNull(taskService);
    }

    @Test
    void testTask1() {
//        Mat blackImageMat = taskService.task1().get(2);
//        List<Mat> bgr = new ArrayList<>();
//        Core.split(blackImageMat, bgr);
//        for(int i = 0; i < blackImageMat.elemSize(); ++i)
//            assertEquals(Core.countNonZero(bgr.get(i)), 0);
    }
}