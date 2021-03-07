package ru.sfedu.cv.property;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenCVProperty {
    private static final Logger log = LogManager.getLogger(OpenCVProperty.class);

    @Value("${open-cv.path}")
    private String lib;

    @Bean
    void loadLib() {
        log.debug(lib);
        System.load(lib);
    }

}
