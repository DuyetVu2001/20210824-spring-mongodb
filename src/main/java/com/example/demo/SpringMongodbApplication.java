package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringMongodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMongodbApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(
            StudentRepository repository, MongoTemplate mongoTemplate
    ) {
        return args -> {
            Address address = new Address(
                    "Viet Nam",
                    "Ha Noi",
                    "100000"
            );

            String email = "duyetvu@gmail.com";
            Student student = new Student(
                    "Duyet",
                    "Vu",
                    email,
                    Gender.MALE,
                    address,
                    List.of("Computer Science", "IT"),
                    BigDecimal.TEN,
                    LocalDateTime.now()
            );

//            usingMongoTemplateAndQuery(repository, mongoTemplate, email, student);

            repository.findStudentsByEmail(email)
                    .ifPresentOrElse(s -> {
                        System.out.println(s + " already exist!");
                    }, () -> {
                        repository.insert(student);
                    });
        };
    }

    private void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        List<Student> students = mongoTemplate.find(query, Student.class);

        if (students.size() > 1) {
            throw new IllegalStateException("Found many students with email " + email);
        }

        if (students.isEmpty()) {
            repository.insert(student);
        } else {
            System.out.println(student + " already exist!");
        }
    }
}
