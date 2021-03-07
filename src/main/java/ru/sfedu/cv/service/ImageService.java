package ru.sfedu.cv.service;

import org.opencv.core.Mat;

public interface ImageService {
    Mat convertBlackByChannel(int channel, Mat image);
}
