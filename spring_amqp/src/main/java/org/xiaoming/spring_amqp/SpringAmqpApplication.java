package org.xiaoming.spring_amqp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.xiaoming.spring_amqp.tut1.RabbitAmqpTutorialsRunner;

@SpringBootApplication
@EnableScheduling
public class SpringAmqpApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAmqpApplication.class, args);
	}

	@Profile("usage_message")
	@Bean
	public CommandLineRunner usage() {
		return args -> {
			System.out.println("This app uses Spring Profiles to control its behavior.\n");
			System.out.println("Sample usage: java -jar spring-boot-sample-amqp-0.0.1.BUILD-SNAPSHOT.jar --spring.profiles.active=hello-world,sender");
		};
	}

	@Profile("!usage_message")
	@Bean
	public CommandLineRunner tutorial() {
		return new RabbitAmqpTutorialsRunner();
	}
}
