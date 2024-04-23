package projectbookstore.service;

import java.util.List;
import projectbookstore.model.Book;

public interface BookService {
    Book save(Book book);

    List findAll();
}
