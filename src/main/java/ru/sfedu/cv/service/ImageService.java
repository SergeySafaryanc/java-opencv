package ru.sfedu.cv.service;

import org.opencv.core.Mat;

import java.util.List;

public interface ImageService {
    Mat convertBlackByChannel(int channel, Mat image);
    Mat convertSobel(Mat image, int dx, int dy);
    Mat convertLaplace(Mat image, int kSize);
    Mat mirrorImage(Mat image, int flipCode);
    Mat unionImage(List<Mat> matList, Mat dst, boolean isVertical);
    Mat repeatImage(Mat image, int ny, int nx);
    Mat resizeImage(Mat image, int width, int height);
    Mat geometryChangeImage(Mat image);
}
