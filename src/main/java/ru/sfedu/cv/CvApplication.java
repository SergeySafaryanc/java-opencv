package ru.sfedu.cv;

import org.opencv.core.Core;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CvApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CvApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }

}
