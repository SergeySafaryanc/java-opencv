package ru.sfedu.cv.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Mat;
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
}
