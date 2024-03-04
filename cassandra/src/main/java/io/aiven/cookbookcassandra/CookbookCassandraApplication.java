package io.aiven.cookbookcassandra;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CookbookCassandraApplication {

	public static void main(String[] args) {
		SpringApplication.run(CookbookCassandraApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(BookRepository bookRepository) {
		return args -> {
			bookRepository.save(new Book("1", "Book 1", "Author 1"));
			Book book = bookRepository.findById("1").get();
			System.out.println(book.getTitle() + " " + book.getAuthor());
		};
	}

}
