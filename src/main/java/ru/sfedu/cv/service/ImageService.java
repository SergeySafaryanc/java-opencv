package ru.sfedu.cv.service;

import org.opencv.core.Mat;

public interface ImageService {
    Mat getBlackChannel(Integer channel, String imgPath);
}