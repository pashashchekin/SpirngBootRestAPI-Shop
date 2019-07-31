package somnium.sarafan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import somnium.sarafan.utils.DailyCouponExpirationTask;

@SpringBootApplication
public class SarafanApplication {

	public static void main(String[] args) {
		SpringApplication.run(SarafanApplication.class, args);
		DailyCouponExpirationTask dailyCouponExpirationTask = new DailyCouponExpirationTask();
		dailyCouponExpirationTask.run();
	}

}
