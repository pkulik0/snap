package dev.pkulik.snap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SnapApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnapApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(LinkRepository linkRepository) {
		return args -> {
			if (!linkRepository.existsById("0")) {
				Link link = new Link("0", "Hello", "World");
				linkRepository.insert(link);
			}
		};
	}
}
