package com.kayulu.component;

import com.kayulu.component.service.ApplicationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MvcTestingExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcTestingExampleApplication.class, args);
	}

	/* New for Section 2.2 */
	@Bean(name = "applicationService")
	ApplicationService getApplicationService() {
		return new ApplicationService();
	}

}
