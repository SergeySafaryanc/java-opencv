package ru.sfedu.cv.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sfedu.cv.service.TaskService;

import java.io.IOException;

@Controller
public class TestImageController {
    private static final Logger log = LogManager.getLogger(TestImageController.class);

    protected final TaskService taskService;

    @Autowired
    public TestImageController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value = "/lab1")
    public String lab1(Model model) throws IOException {
        model.addAttribute("instanceMap", taskService.task1ToView());
        return "image";
    }

    @GetMapping(value = "/lab2/1/1")
    public String sobel(Model model,
                        @RequestParam int dx,
                        @RequestParam int dy) throws IOException {
        model.addAttribute("instanceMap", taskService.task2ToViewSobel(dx, dy));
        return "image";
    }

    @GetMapping(value = "/lab2/1/2")
    public String sobel(Model model,
                        @RequestParam int xSize) throws IOException {
        model.addAttribute("instanceMap", taskService.task2ToViewLaplace(xSize));
        return "image";
    }



}
