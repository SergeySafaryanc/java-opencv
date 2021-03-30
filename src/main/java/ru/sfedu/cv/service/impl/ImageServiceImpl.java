package ru.sfedu.cv.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sfedu.cv.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
    private static final Logger log = LogManager.getLogger(ImageServiceImpl.class);

    @Override
    public Mat convertBlackByChannel(int channel, Mat image) {
        int totalBytes = (int) (image.total() * image.elemSize());
        byte[] buffer = new byte[totalBytes];
        image.get(0, 0, buffer);
        for (int i = channel; i < totalBytes; i += image.elemSize()) {
            buffer[i] = 0;
        }
        image.put(0, 0, buffer);
        return image;
    }

    @Override
    public Mat convertSobel(Mat image, int dx, int dy) {
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
        Mat dstSobel = new Mat();
        Imgproc.Sobel(grayImage, dstSobel, CvType.CV_32F, dx, dy);
        Core.convertScaleAbs(dstSobel, dstSobel);
        return dstSobel;
    }

    @Override
    public Mat convertLaplace(Mat image, int kSize) {
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
        Mat dstLaplace = new Mat();
        Imgproc.Laplacian(grayImage, dstLaplace, kSize);
        Core.convertScaleAbs(dstLaplace, dstLaplace);
        return dstLaplace;
    }
}
