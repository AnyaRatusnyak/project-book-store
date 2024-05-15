package projectbookstore.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import projectbookstore.dto.book.BookDto;
import projectbookstore.dto.book.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    BookDto findById(Long id);

    List<BookDto> findAll(Pageable pageable);

    BookDto update(Long id, CreateBookRequestDto requestDto);

    void deleteById(Long id);
}
