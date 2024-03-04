package io.aiven.cookbookclickhouse;

import java.sql.Statement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.clickhouse.jdbc.ClickHouseConnection;
import com.clickhouse.jdbc.ClickHouseDataSource;

@SpringBootApplication
public class CookbookClickhouseApplication {

	@Value("${clickhouse.url}")
	private String clickhouseUrl;

	@Value("${clickhouse.username}")
	private String clickhouseUsername;

	@Value("${clickhouse.password}")
	private String clickhousePassword;

	public static void main(String[] args) {
		SpringApplication.run(CookbookClickhouseApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return args -> {
			String connString = clickhouseUrl;
			ClickHouseDataSource database = new ClickHouseDataSource(connString);
			ClickHouseConnection connection = database.getConnection(clickhouseUsername, clickhousePassword);
			Statement statement = connection.createStatement();
			ResultSet result_set = statement.executeQuery("SELECT 1 AS one");
			while (result_set.next()) {
				System.out.println("RESULT : " + result_set.getInt("one"));
			}
		};
	}

}
