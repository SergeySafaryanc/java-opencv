package ru.sfedu.cv.service.impl;

import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sfedu.cv.service.ImageService;


@SpringBootTest
class ImageServiceImplTest {
    private static final String TEST_IMAGE = "/home/sergey/IdeaProjects/cv/imgs/haskell-opencv-logo.png";

    @Autowired
    protected ImageService imageService;

    @Test
    void initNativeLibrary() {

    }

    @Test
    void getBlackChannel() {
        Mat mat = imageService.getBlackChannel(5, TEST_IMAGE);

        Imgcodecs.imwrite("/home/sergey/IdeaProjects/cv/imgs/0noise.png", mat);
    }

}