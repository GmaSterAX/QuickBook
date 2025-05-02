package com.quickbook21.qb21;

import com.quickbook21.qb21.Repositories.UserRepository;
import com.quickbook21.qb21.models.Users;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class Qb21Application {

	public static void main(String[] args) {
		SpringApplication.run(Qb21Application.class, args);
	}

}
