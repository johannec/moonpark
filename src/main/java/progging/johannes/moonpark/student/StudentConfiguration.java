package progging.johannes.moonpark.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.APRIL;
import static java.time.Month.JANUARY;

@Configuration
public class StudentConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student johannes = new Student("Johannes", "johannes@email.com", LocalDate.of(1994, APRIL, 20));
            Student tester = new Student("Tester", "tester@email.com", LocalDate.of(2001, JANUARY, 2));
            Student donald = new Student("Donald", "donald@email.com", LocalDate.of(1994, APRIL, 20));
            studentRepository.saveAll(List.of(johannes, tester, donald));
        };
    }
}
