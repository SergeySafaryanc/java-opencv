package ru.sfedu.cv.service;

import org.opencv.core.Mat;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ConversionService {
    Mat bufferedImageToMat(BufferedImage bufferedImage);

    BufferedImage multipartToBufferedImage(MultipartFile multipart) throws IOException;
    BufferedImage matToBufferedImage(Mat image);

    String bufferedImageToWebImg(BufferedImage bufferedImage) throws IOException;
    String matToWebImg(Mat image) throws IOException;

    String saveBufferedImageOnDisk(BufferedImage bufferedImage, String basePath) throws IOException;
    String saveMatImageOnDisk(Mat image, String basePath);
}
