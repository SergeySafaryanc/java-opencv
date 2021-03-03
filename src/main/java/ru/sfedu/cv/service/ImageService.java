package ru.sfedu.cv.service;

import org.opencv.core.Mat;

public interface ImageService {
    Mat getLab2(Integer channel, String imgPath);
    Mat getSobel(String imgPath);
    Mat getLaplace(String imgPath);
}
