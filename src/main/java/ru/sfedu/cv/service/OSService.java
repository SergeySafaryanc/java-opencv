package ru.sfedu.cv.service;

import ru.sfedu.cv.Constant;

public interface OSService {
    Constant.OS_TYPE getType();
    void loadLibrary();
}
