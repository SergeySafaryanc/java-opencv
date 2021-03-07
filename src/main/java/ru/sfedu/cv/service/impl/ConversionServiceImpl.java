package ru.sfedu.cv.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sfedu.cv.service.ConversionService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ConversionServiceImpl implements ConversionService {
    private static final Logger log = LogManager.getLogger(ConversionServiceImpl.class);

    @Override
    public BufferedImage multipartToBufferedImage(MultipartFile multipart) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(multipart.getBytes());
        return ImageIO.read(inputStream);
    }

    @Override
    public Mat bufferedImageToMat(BufferedImage bufferedImage) {
        Mat mat = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }

    @Override
    public BufferedImage matToBufferedImage(Mat image) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (image.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        int bufferSize = image.channels() * image.cols() * image.rows();
        byte[] bytes = new byte[bufferSize];
        image.get(0, 0, bytes);
        BufferedImage imageBuffer = new BufferedImage(image.cols(), image.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) imageBuffer.getRaster().getDataBuffer()).getData();
        System.arraycopy(bytes, 0, targetPixels, 0, bytes.length);
        return imageBuffer;
    }

    @Override
    public String bufferedImageToWebImg(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
        return Base64.encodeBase64String(byteArrayOutputStream.toByteArray());
    }

    @Override
    public String matToWebImg(Mat image) throws IOException {
        return bufferedImageToWebImg(matToBufferedImage(image));
    }

    @Override
    public String saveBufferedImageOnDisk(BufferedImage bufferedImage, String basePath) throws IOException {
        final String filePath = buildImageName(basePath);
        File outputFile = new File(filePath);
        ImageIO.write(bufferedImage, "jpg", outputFile);
        return filePath;
    }

    @Override
    public String saveMatImageOnDisk(Mat image, String basePath) {
        final String filePath = buildImageName(basePath);
        Imgcodecs.imwrite(filePath, image);
        return filePath;
    }

    public String buildImageName(String base) {
        return String.format("%s/%d.jpg", base, System.nanoTime());
    }

}
