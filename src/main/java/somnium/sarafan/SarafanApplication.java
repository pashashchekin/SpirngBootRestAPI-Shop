package somnium.sarafan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SarafanApplication {

	public static void main(String[] args) {
		SpringApplication.run(SarafanApplication.class, args);
	}

}
