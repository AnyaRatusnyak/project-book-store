package projectbookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import projectbookstore.service.BookService;

@SpringBootApplication
public class ProjectBookStoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(ProjectBookStoreApplication.class, args);
    }
}
