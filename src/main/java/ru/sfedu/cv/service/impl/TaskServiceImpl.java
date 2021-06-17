package ru.sfedu.cv.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sfedu.cv.service.ConversionService;
import ru.sfedu.cv.service.ImageService;
import ru.sfedu.cv.service.TaskService;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.opencv.core.CvType.CV_8UC3;
import static org.opencv.imgcodecs.Imgcodecs.IMREAD_COLOR;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger log = LogManager.getLogger(TaskServiceImpl.class);

    @Value("${show.image}")
    private String showImage;
    @Value("${show.square}")
    private String showSquare;

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

    @Override
    public Map<Integer, String> task3ToMorphingRect() throws IOException {
        double[] sizes = {3, 5, 7, 9, 13, 15};

        Map<Integer, String> resultMap = new HashMap<>();
        Mat defaultMat = Imgcodecs.imread(showImage);

        AtomicInteger integer = new AtomicInteger(0);
        for (double size: sizes) {
            Mat morphRect = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(size, size));

            Mat dst1 = defaultMat.clone();
            Imgproc.dilate(defaultMat, dst1, morphRect);
            resultMap.put(integer.incrementAndGet(), conversionService.matToWebImg(dst1));

            Mat dst1_1 = defaultMat.clone();
            Imgproc.morphologyEx(defaultMat, dst1_1, Imgproc.MORPH_GRADIENT, morphRect);
            resultMap.put(integer.incrementAndGet(), conversionService.matToWebImg(dst1_1));

            Mat dst1_2 = defaultMat.clone();
            Imgproc.morphologyEx(defaultMat, dst1_2, Imgproc.MORPH_BLACKHAT, morphRect);
            resultMap.put(integer.incrementAndGet(), conversionService.matToWebImg(dst1_2));
        }
        return resultMap;
    }

    @Override
    public Map<Integer, String> task3ToMorphingEllipse() throws IOException {
        double[] sizes = {3, 5, 7, 9, 13, 15};

        Map<Integer, String> resultMap = new HashMap<>();
        Mat defaultMat = Imgcodecs.imread(showImage);

        AtomicInteger integer = new AtomicInteger(0);
        for (double size: sizes) {
            Mat morphEllipse = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(size, size));

            Mat dst1 = defaultMat.clone();
            Imgproc.dilate(defaultMat, dst1, morphEllipse);
            resultMap.put(integer.incrementAndGet(), conversionService.matToWebImg(dst1));

            Mat dst1_1 = defaultMat.clone();
            Imgproc.morphologyEx(defaultMat, dst1_1, Imgproc.MORPH_GRADIENT, morphEllipse);
            resultMap.put(integer.incrementAndGet(), conversionService.matToWebImg(dst1_1));

            Mat dst1_2 = defaultMat.clone();
            Imgproc.morphologyEx(defaultMat, dst1_2, Imgproc.MORPH_BLACKHAT, morphEllipse);
            resultMap.put(integer.incrementAndGet(), conversionService.matToWebImg(dst1_2));
        }
        return resultMap;
    }

    @Override
    public Map<Integer, String> task4ToWarp() throws IOException {
        Map<Integer, String> resultMap = new HashMap<>();
        Mat defaultMat = Imgcodecs.imread(showImage);

        Mat transMat = new Mat(2, 3, CvType.CV_64FC1);
        MatOfPoint2f src = new MatOfPoint2f(
                new Point(0, 0),
                new Point(defaultMat.cols(), 0),
                new Point(0, defaultMat.rows()),
                new Point(defaultMat.cols(), defaultMat.rows())
        );
        int x = 50;
        int y = 50;
        MatOfPoint2f target = new MatOfPoint2f(
                new Point(x, y),
                new Point(defaultMat.cols() - x, 0),
                new Point(0, defaultMat.rows() - y),
                new Point(defaultMat.cols() - x, defaultMat.rows() - y)
        );

        Mat matWarp = Imgproc.getPerspectiveTransform(src, target);
        Mat res = new Mat();
        Imgproc.warpPerspective(defaultMat, res, matWarp, defaultMat.size());
        resultMap.put(0, conversionService.matToWebImg(res));

        return resultMap;
    }

    @Override
    public Map<Integer, String> task5ToFill(Integer initVal) throws IOException {
        Map<Integer, String> resultMap = new HashMap<>();
        Mat defaultMat = Imgcodecs.imread(showImage);

        Point seedPoint = new Point(0,0);
        Scalar newVal = new Scalar(0,255,0);
        Scalar loDiff = new Scalar(initVal,initVal,initVal);
        Scalar upDiff = new Scalar(initVal,initVal,initVal);
        Mat mask = new Mat();

        Imgproc.floodFill(defaultMat, mask, seedPoint, newVal, new Rect(), loDiff, upDiff,
                Imgproc.FLOODFILL_FIXED_RANGE + 8);
        resultMap.put(0, conversionService.matToWebImg(defaultMat));

        return resultMap;
    }

    @Override
    public Map<Integer, String> task5ToPyr(int factor, boolean direction) throws IOException {
        Map<Integer, String> resultMap = new HashMap<>();
        Mat defaultMat = noiseMat(350, 350);

        Mat mask = new Mat();
        resultMap.put(0, conversionService.matToWebImg(defaultMat));

        Imgproc.pyrDown(defaultMat, mask);
        resultMap.put(1, conversionService.matToWebImg(mask));

        Imgproc.pyrUp(mask, mask);
        resultMap.put(2, conversionService.matToWebImg(mask));

        Core.subtract(defaultMat, mask, mask);
        resultMap.put(3, conversionService.matToWebImg(mask));

        return resultMap;
    }

    @Override
    public Map<Integer, String> task5ToSquare() throws IOException {
        Map<Integer, String> resultMap = new HashMap<>();
        Mat defaultMat = Imgcodecs.imread(showSquare);

        Mat grayImage = new Mat();
        Imgproc.cvtColor(defaultMat, grayImage, Imgproc.COLOR_BGR2GRAY);
        resultMap.put(0, conversionService.matToWebImg(grayImage));

        Mat denoisingImage = new Mat();
        Photo.fastNlMeansDenoising(grayImage, denoisingImage);
        resultMap.put(1, conversionService.matToWebImg(denoisingImage));

        Mat histogramEqualizationImage = new Mat();
        Imgproc.equalizeHist(denoisingImage, histogramEqualizationImage);
        resultMap.put(2, conversionService.matToWebImg(histogramEqualizationImage));


        Mat morphologicalOpeningImage = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Imgproc.morphologyEx(histogramEqualizationImage, morphologicalOpeningImage, Imgproc.MORPH_RECT, kernel);
        resultMap.put(3, conversionService.matToWebImg(morphologicalOpeningImage));

        Mat subtractImage = new Mat();
        Core.subtract(histogramEqualizationImage, morphologicalOpeningImage, subtractImage);
        resultMap.put(4, conversionService.matToWebImg(subtractImage));

        Mat thresholdImage = new Mat();
        double threshold = Imgproc.threshold(subtractImage, thresholdImage, 50, 255, Imgproc.THRESH_OTSU);
        resultMap.put(5, conversionService.matToWebImg(thresholdImage));
        thresholdImage.convertTo(thresholdImage, CvType.CV_16SC1);

        Mat edgeImage = new Mat();
        thresholdImage.convertTo(thresholdImage, CvType.CV_8U);
        Imgproc.Canny(thresholdImage, edgeImage, threshold, threshold * 3, 3, true);
        resultMap.put(6, conversionService.matToWebImg(edgeImage));

        Mat dilatedImage = new Mat();
        Imgproc.dilate(thresholdImage, dilatedImage, kernel);
        resultMap.put(7, conversionService.matToWebImg(dilatedImage));

        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(dilatedImage, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        contours.sort(Collections.reverseOrder(Comparator.comparing(Imgproc::contourArea)));
//        System.out.println(contours.size());
        AtomicInteger integer = new AtomicInteger(8);
        for (MatOfPoint contour : contours.subList(0, 1)) {
            log.debug(Imgproc.contourArea(contour));
            MatOfPoint2f point2f = new MatOfPoint2f();
            MatOfPoint2f approxContour2f = new MatOfPoint2f();
            MatOfPoint approxContour = new MatOfPoint();
            contour.convertTo(point2f, CvType.CV_32FC2);
            double arcLength = Imgproc.arcLength(point2f, true);
            Imgproc.approxPolyDP(point2f, approxContour2f, 0.03 * arcLength, true);
            approxContour2f.convertTo(approxContour, CvType.CV_32S);
            Rect rect = Imgproc.boundingRect(approxContour);
            double ratio = (double) rect.height / rect.width;
            if (Math.abs(0.3 - ratio) > 0.15) {
                continue;
            }
            Mat submat = defaultMat.submat(rect);
            Imgproc.resize(submat, submat, new Size(400, 400 * ratio));
            resultMap.put(integer.incrementAndGet(), conversionService.matToWebImg(submat));
        }

        return resultMap;
    }

    protected Mat noiseMat(int height, int width) {
        Mat noiseMat = new Mat(new Size(width, height), CV_8UC3, new Scalar(0, 0, 0));
        Core.randn(noiseMat, 20, 50);
        Core.add(noiseMat, noiseMat, noiseMat);
        return noiseMat;
    }

}
