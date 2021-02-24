package ru.sfedu.cv.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sfedu.cv.Constant;
import ru.sfedu.cv.service.ImageService;
import ru.sfedu.cv.service.OSService;

import java.util.List;
import java.util.Locale;

@Service
public class ImageServiceImpl implements ImageService {
    private static final Logger log = LogManager.getLogger(ImageServiceImpl.class);

    protected final List<OSService> osServices;

    @Autowired
    public ImageServiceImpl(List<OSService> osServices) {
        this.osServices = osServices;

        osServices.stream()
                .filter(os -> os.getType().equals(getOS_Type()))
                .forEach(OSService::loadLibrary);
    }


    @Override
    public Mat getBlackChannel(Integer channel, String imgPath) {
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

    protected Constant.OS_TYPE getOS_Type() {
        final String os = System.getProperty("os.name").toUpperCase(Locale.ENGLISH);
        return switch (os) {
            case "WINDOWS" -> Constant.OS_TYPE.WINDOWS;
            case "LINUX" -> Constant.OS_TYPE.LINUX;
            default -> Constant.OS_TYPE.OTHER;
        };
    }


}
