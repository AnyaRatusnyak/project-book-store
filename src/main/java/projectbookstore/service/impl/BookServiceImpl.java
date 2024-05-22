package projectbookstore.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import projectbookstore.dto.book.BookDto;
import projectbookstore.dto.book.BookDtoWithoutCategoryIds;
import projectbookstore.dto.book.CreateBookRequestDto;
import projectbookstore.exception.EntityNotFoundException;
import projectbookstore.mapper.BookMapper;
import projectbookstore.model.Book;
import projectbookstore.model.Category;
import projectbookstore.repository.BookRepository;
import projectbookstore.repository.CategoryRepository;
import projectbookstore.service.BookService;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        validateCategoriesExist(requestDto.getCategoryIds());
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
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long id, Pageable pageable) {
        return bookRepository
                .findAllByCategoryId(id,pageable)
                .stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto requestDto) {
        validateCategoriesExist(requestDto.getCategoryIds());
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can`t find a book with id: " + id);
        }
        Book updatedBook = bookMapper.toModel(requestDto);
        updatedBook.setId(id);
        return bookMapper.toDto(bookRepository.save(updatedBook));
    }

    @Override
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can`t find a book with id: " + id);
        }

        bookRepository.deleteById(id);
    }

    public void validateCategoriesExist(Set<Long> categoryIds) {
        List<Category> categoriesDB = categoryRepository.findAll();
        Set<Long> existingCategoryIds = categoriesDB.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        Set<Long> nonExistingCategoryIds = new HashSet<>(categoryIds);
        nonExistingCategoryIds.removeAll(existingCategoryIds);
        if (!nonExistingCategoryIds.isEmpty()) {
            throw new EntityNotFoundException("Categories with ids "
                    + nonExistingCategoryIds + " do not exist.");
        }
    }

}
