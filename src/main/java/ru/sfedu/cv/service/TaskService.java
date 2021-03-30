package ru.sfedu.cv.service;

import org.opencv.core.Mat;

import java.io.IOException;
import java.util.Map;

public interface TaskService {
    Map<Integer, Mat> task1();
    Map<Integer, String> task1ToView() throws IOException;

    Map<Integer, String> task2ToViewSobel(int dx, int dy) throws IOException;
    Map<Integer, String> task2ToViewLaplace(int xSize) throws IOException;
}
