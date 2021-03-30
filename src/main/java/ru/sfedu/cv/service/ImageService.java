package ru.sfedu.cv.service;

import org.opencv.core.Mat;

public interface ImageService {
    Mat convertBlackByChannel(int channel, Mat image);
    Mat convertSobel(Mat image, int dx, int dy);
    Mat convertLaplace(Mat image, int kSize);

}
