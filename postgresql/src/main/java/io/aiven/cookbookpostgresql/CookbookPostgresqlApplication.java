package io.aiven.cookbookpostgresql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class CookbookPostgresqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(CookbookPostgresqlApplication.class, args);
	}

}
