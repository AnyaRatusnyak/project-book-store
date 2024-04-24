package projectbookstore.repository;

import java.util.List;
import java.util.Optional;
import projectbookstore.model.Book;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();
}
