package ru.hogwarts.school.service.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class ThreadServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(ThreadServiceImpl.class);
    private final StudentRepository studentRepository;

    public ThreadServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void thread(){
        List<Student> all = studentRepository.findAll();
        logStudent(all.get(0));
        logStudent(all.get(1));
        new Thread(() -> {
            logStudent(all.get(2));
            logStudent(all.get(3));
        }).start();
        new Thread(() -> {
                logStudent(all.get(4));
                logStudent(all.get(5));
        }).start();

    }
    public void threadTwo(){
        List<Student> all = studentRepository.findAll();
        logStudent_synchronized(all.get(0));
        logStudent_synchronized(all.get(1));
        new Thread(() -> {
            logStudent_synchronized(all.get(2));
            logStudent_synchronized(all.get(3));
        }).start();
        new Thread(() -> {
            logStudent_synchronized(all.get(4));
            logStudent_synchronized(all.get(5));
        }).start();

    }
    private void logStudent(Student student){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info(student.toString());
    }
    private synchronized void logStudent_synchronized(Student student){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info(student.toString());
    }
}
