package projectbookstore;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import projectbookstore.model.Book;
import projectbookstore.service.BookService;

@SpringBootApplication
public class ProjectBookStoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(ProjectBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book = new Book();
                book.setAuthor("Shield");
                book.setTitle("Java");
                book.setPrice(BigDecimal.valueOf(100));
                book.setDescription("programming book");
                book.setCoverImage("image");
                book.setIsbn("1235");
                bookService.save(book);
                System.out.println(bookService.findAll());
            }
        };
    }
}
