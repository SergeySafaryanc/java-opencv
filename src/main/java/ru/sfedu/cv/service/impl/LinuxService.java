package ru.sfedu.cv.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sfedu.cv.Constant;
import ru.sfedu.cv.service.OSService;

@Service
public class LinuxService implements OSService {
    private static final Logger log = LogManager.getLogger(LinuxService.class);

    @Value("${native.opencv.linux}")
    private String pathToNativeLib;

    @Override
    public Constant.OS_TYPE getType() {
        return Constant.OS_TYPE.LINUX;
    }

    @Override
    public void loadLibrary() {
        System.load(pathToNativeLib);
    }
}
