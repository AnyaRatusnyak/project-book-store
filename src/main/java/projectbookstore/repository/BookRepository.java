package projectbookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbookstore.model.Book;

public interface BookRepository extends JpaRepository<Book,Long> {
}
