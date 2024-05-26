package progging.johannes.moonpark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class MoonParkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoonParkApplication.class, args);
	}


}
