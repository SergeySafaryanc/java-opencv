package ru.sfedu.cv.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sfedu.cv.service.ImageService;

import javax.annotation.PostConstruct;


@Service
public class ImageServiceImpl implements ImageService {
    private static final Logger log = LogManager.getLogger(ImageServiceImpl.class);

    @Value("${native.opencv.path}")
    private String pathToNativeLib;

    @PostConstruct
    void initNativeLibrary() {
        System.load(pathToNativeLib);
    }

    @Override
    public Mat getLab2(Integer channel, String imgPath) {
        Mat img = Imgcodecs.imread(imgPath);
        long totalBytes = (img.total() + img.elemSize());
        byte[] buffer = new byte[Math.toIntExact(totalBytes)];
        img.get(0, 0, buffer);
        for (int i = channel; i < totalBytes; i += img.elemSize()) {
            buffer[i] = 0;
        }
        img.put(0,0, buffer);
        return img;
    }

    @Override
    public Mat getSobel(String imgPath) {
        Mat grayImage = new Mat();
        Mat srcImage = Imgcodecs.imread(imgPath, Imgcodecs.IMREAD_COLOR);
        Imgproc.cvtColor(srcImage, grayImage, Imgproc.COLOR_BGR2GRAY);
        Mat dstSobelX = new Mat();
        Imgproc.Sobel(grayImage, dstSobelX, CvType.CV_32F, 1, 0);
        Mat dstSobelY = new Mat();
        Imgproc.Sobel(grayImage, dstSobelY, CvType.CV_32F, 0, 1);
        return dstSobelY;
//        Imgcodecs.imwrite(imgPath, dstSobelX);
//        Imgcodecs.imwrite(destDirPath + "SobelY_" + srcFileName, dstSobelY);
    }

    @Override
    public Mat getLaplace(String imgPath) {
        return null;
    }

}
