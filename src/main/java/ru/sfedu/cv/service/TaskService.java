package ru.sfedu.cv.service;

import org.opencv.core.Mat;

import java.io.IOException;
import java.util.Map;

public interface TaskService {
    Map<Integer, Mat> task1();
    Map<Integer, String> task1ToView() throws IOException;

    Map<Integer, String> task2ToViewSobel(int dx, int dy) throws IOException;
    Map<Integer, String> task2ToViewLaplace(int xSize) throws IOException;
    Map<Integer, String> task2ToViewMirror() throws IOException;
    Map<Integer, String> task2ToViewUnion() throws IOException;
    Map<Integer, String> task2ToViewRepeat(int ny, int nx) throws IOException;
    Map<Integer, String> task2ToViewResize(int width, int height) throws IOException;
    Map<Integer, String> task2ToViewGeometryChange() throws IOException;

    Map<Integer, String> task3ToMorphingRect() throws IOException;
    Map<Integer, String> task3ToMorphingEllipse() throws IOException;

    Map<Integer, String> task4ToWarp() throws IOException;

}
