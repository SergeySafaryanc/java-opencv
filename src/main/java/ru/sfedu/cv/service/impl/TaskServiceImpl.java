package ru.sfedu.cv.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sfedu.cv.service.ConversionService;
import ru.sfedu.cv.service.ImageService;
import ru.sfedu.cv.service.TaskService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.opencv.core.CvType.CV_8UC3;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger log = LogManager.getLogger(TaskServiceImpl.class);

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

    protected Mat noiseMat(int height, int width) {
        Mat noiseMat = new Mat(new Size(width, height), CV_8UC3, new Scalar(0, 0, 0));
        Core.randn(noiseMat, 20, 50);
        Core.add(noiseMat, noiseMat, noiseMat);
        return noiseMat;
    }

}
