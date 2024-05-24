package progging.johannes.studenttemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "progging.johannes.studenttemplate.student")
public class StudentTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentTemplateApplication.class, args);
	}


}
