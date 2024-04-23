package projectbookstore.repository;

import java.util.List;
import projectbookstore.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
