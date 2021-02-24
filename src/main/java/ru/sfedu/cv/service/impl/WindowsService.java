package ru.sfedu.cv.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sfedu.cv.Constant;
import ru.sfedu.cv.service.OSService;

@Service
public class WindowsService implements OSService {

    @Value("${native.opencv.windows}")
    private String pathToNativeLib;

    @Override
    public Constant.OS_TYPE getType() {
        return Constant.OS_TYPE.WINDOWS;
    }

    @Override
    public void loadLibrary() {
        System.load(pathToNativeLib);
    }

}
