package ru.sfedu.cv.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sfedu.cv.service.ConversionService;
import ru.sfedu.cv.service.ImageService;
import ru.sfedu.cv.service.TaskService;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.opencv.core.CvType.CV_8UC3;
import static org.opencv.imgcodecs.Imgcodecs.IMREAD_COLOR;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger log = LogManager.getLogger(TaskServiceImpl.class);

    @Value("${show.image}")
    private String showImage;

    protected final ImageService imageService;
    protected final ConversionService conversionService;

    @Autowired
    public TaskServiceImpl(ImageService imageService, ConversionService conversionService) {
        this.imageService = imageService;
        this.conversionService = conversionService;
    }

    @Override
    public Map<Integer, Mat> task1() {
        Map<Integer, Mat> resultMap = new HashMap<>();

        Mat noiseMat = noiseMat(250, 250);
        resultMap.put(-1, noiseMat);

        Mat rmBlue = imageService.convertBlackByChannel(0, noiseMat);
        resultMap.put(0, rmBlue);

        Mat rmGreen = imageService.convertBlackByChannel(1, rmBlue);
        resultMap.put(1, rmGreen);

        Mat rmRed = imageService.convertBlackByChannel(2, rmGreen);
        resultMap.put(2, rmRed);

        return resultMap;
    }

    @Override
    public Map<Integer, String> task1ToView() throws IOException {
        Map<Integer, String> resultMap = new HashMap<>();

        Mat noiseMat = noiseMat(260, 260);
        resultMap.put(-1, conversionService.matToWebImg(noiseMat));

        Mat rmBlue = imageService.convertBlackByChannel(0, noiseMat);
        resultMap.put(0, conversionService.matToWebImg(rmBlue));

        Mat rmGreen = imageService.convertBlackByChannel(1, rmBlue);
        resultMap.put(1, conversionService.matToWebImg(rmGreen));

        Mat rmRed = imageService.convertBlackByChannel(2, rmGreen);
        resultMap.put(2, conversionService.matToWebImg(rmRed));

        return resultMap;
    }

    @Override
    public Map<Integer, String> task2ToViewSobel(int dx, int dy) throws IOException {
        Map<Integer, String> resultMap = new HashMap<>();
        Mat noiseMat = Imgcodecs.imread(showImage);

        Mat noiseToSobelMat = imageService.convertSobel(noiseMat, dx, dy);
        resultMap.put(0, conversionService.matToWebImg(noiseToSobelMat));

        return resultMap;
    }

    @Override
    public Map<Integer, String> task2ToViewLaplace(int xSize) throws IOException {
        Map<Integer, String> resultMap = new HashMap<>();
        Mat noiseMat = Imgcodecs.imread(showImage);

        Mat noiseToLaplaceMat = imageService.convertLaplace(noiseMat, xSize);
        resultMap.put(1, conversionService.matToWebImg(noiseToLaplaceMat));

        return resultMap;
    }

    @Override
    public Map<Integer, String> task2ToViewMirror() throws IOException {
        Map<Integer, String> resultMap = new HashMap<>();

        Mat defaultMat = Imgcodecs.imread(showImage);
        resultMap.put(0, conversionService.matToWebImg(defaultMat));

        Mat mirrorFlipCode0 = imageService.mirrorImage(defaultMat, 0);
        resultMap.put(1, conversionService.matToWebImg(mirrorFlipCode0));

        Mat mirrorFlipCode1 = imageService.mirrorImage(defaultMat, 1);
        resultMap.put(2, conversionService.matToWebImg(mirrorFlipCode1));

        Mat mirrorFlipCodeMinus1 = imageService.mirrorImage(defaultMat, -1);
        resultMap.put(3, conversionService.matToWebImg(mirrorFlipCodeMinus1));

        return resultMap;
    }

    @Override
    public Map<Integer, String> task2ToViewUnion() throws IOException {
        Mat defaultMat1 = Imgcodecs.imread(showImage);
        Mat defaultMat2 = Imgcodecs.imread(showImage);

        Map<Integer, String> resultMap = new HashMap<>();
        Mat list = imageService.unionImage(defaultMat1, defaultMat2);

        resultMap.put(0, conversionService.matToWebImg(list));
        return resultMap;
    }

    @Override
    public Map<Integer, String> task2ToViewRepeat(int ny, int nx) throws IOException {
        Map<Integer, String> resultMap = new HashMap<>();

        Mat defaultMat = Imgcodecs.imread(showImage);

        Mat rotationImage = imageService.repeatImage(defaultMat, ny, nx);
        resultMap.put(0, conversionService.matToWebImg(rotationImage));

        return resultMap;
    }

    @Override
    public Map<Integer, String> task2ToViewResize(int width, int height) throws IOException {
        Map<Integer, String> resultMap = new HashMap<>();

        Mat defaultMat = Imgcodecs.imread(showImage, IMREAD_COLOR);
        Mat resizeImage = imageService.resizeImage(defaultMat, width, height);
        resultMap.put(0, conversionService.matToWebImg(resizeImage));

        return resultMap;
    }

    @Override
    public Map<Integer, String> task2ToViewGeometryChange() throws IOException {
        Map<Integer, String> resultMap = new HashMap<>();
        Mat defaultMat = Imgcodecs.imread(showImage);

        Mat geometryChange = imageService.geometryChangeImage(defaultMat);
        resultMap.put(0, conversionService.matToWebImg(geometryChange));

        return resultMap;
    }

    protected Mat noiseMat(int height, int width) {
        Mat noiseMat = new Mat(new Size(width, height), CV_8UC3, new Scalar(0, 0, 0));
        Core.randn(noiseMat, 20, 50);
        Core.add(noiseMat, noiseMat, noiseMat);
        return noiseMat;
    }

}
