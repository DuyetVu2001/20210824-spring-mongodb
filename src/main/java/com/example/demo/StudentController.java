package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<Student> getStudent() {
        return studentService.getAllStudents();
    }
}
