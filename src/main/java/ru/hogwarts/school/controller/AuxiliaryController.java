package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.implement.ThreadServiceImpl;

import java.util.stream.Stream;

@RestController
@RequestMapping("/auxiliary")
public class AuxiliaryController {
    private final ThreadServiceImpl threadService;

    public AuxiliaryController(ThreadServiceImpl threadService) {
        this.threadService = threadService;
    }

    @GetMapping
    public Long sumNumber(){
        long start = System.currentTimeMillis();
        long sum = Stream
                .iterate(1, a ->  a +1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, Integer::sum);
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
        return sum;
    }
    @GetMapping("/thread")
    public void startThreads (){
         threadService.thread();
    }
        @GetMapping("/thread-synchronized")
    public void startThreadsTwo (){
         threadService.threadTwo();
    }
}
