package br.com.fiap.epictask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.fiap.epictask.repository.UserRepository;

@SpringBootApplication
public class NewEpickTaskApplication {

	@Autowired
	private UserRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(NewEpickTaskApplication.class, args);
		loginPage();
	}
	
	public static void loginPage() {
		System.out.println("oi");
	}

}
