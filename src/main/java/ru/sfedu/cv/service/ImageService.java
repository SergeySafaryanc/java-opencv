package ru.sfedu.cv.service;

import org.opencv.core.Mat;

public interface ImageService {
    Mat convertBlackByChannel(int channel, Mat image);
    Mat convertSobel(Mat image, int dx, int dy);
    Mat convertLaplace(Mat image, int kSize);
    Mat mirrorImage(Mat image, int flipCode);
    Mat unionImage(Mat mat, Mat dst);
    Mat repeatImage(Mat image, int ny, int nx);
    Mat resizeImage(Mat image, int width, int height);
    Mat geometryChangeImage(Mat image);
}
