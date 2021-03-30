package ru.sfedu.cv.property;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class OpenCVProperty {
    private static final Logger log = LogManager.getLogger(OpenCVProperty.class);

    @Value("${open-cv.path}")
    private String lib;

    @PostConstruct
    void initOpenCV() {
        log.debug(lib);
        System.load(lib);
    }


}
