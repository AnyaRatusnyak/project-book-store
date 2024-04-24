package projectbookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbookstore.dto.BookDto;
import projectbookstore.dto.CreateBookRequestDto;
import projectbookstore.exception.EntityNotFoundException;
import projectbookstore.mapper.BookMapper;
import projectbookstore.model.Book;
import projectbookstore.repository.BookRepository;
import projectbookstore.service.BookService;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Can`t find a book with id: %d", id)
        ));
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
