package projectbookstore.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static projectbookstore.utils.TestDataUtils.ID_1;
import static projectbookstore.utils.TestDataUtils.createBook;
import static projectbookstore.utils.TestDataUtils.createBookRequestDto;
import static projectbookstore.utils.TestDataUtils.createCategories;
import static projectbookstore.utils.TestDataUtils.getBookDto;
import static projectbookstore.utils.TestDataUtils.getBookDtoWithoutCategories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import projectbookstore.dto.book.BookDto;
import projectbookstore.dto.book.BookDtoWithoutCategoryIds;
import projectbookstore.dto.book.CreateBookRequestDto;
import projectbookstore.exception.EntityNotFoundException;
import projectbookstore.mapper.BookMapper;
import projectbookstore.model.Book;
import projectbookstore.model.Category;
import projectbookstore.repository.BookRepository;
import projectbookstore.repository.CategoryRepository;
import projectbookstore.utils.TestDataUtils;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Verify save() method works")
    void save_CreateBookRequestDto_ReturnsBookDto() {
        // Given
        CreateBookRequestDto requestDto = createBookRequestDto();
        Book book = createBook();
        BookDto bookDto = getBookDto(book);
        List<Category> categories = createCategories();

        // Mocking behavior
        when(categoryRepository.findAll()).thenReturn(categories);
        when(bookMapper.toModel(any(CreateBookRequestDto.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDto);

        // When
        BookDto result = bookService.save(requestDto);

        // Then
        assertEquals(getBookDto(book), result);
    }

    @Test
    @DisplayName("Verify findById() method works")
    void findById_ExistingId_ReturnsBookDto() {
        // Given
        Long id = ID_1;
        Book book = createBook();
        BookDto bookDto = getBookDto(book);

        // Mocking behavior
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        // When
        BookDto result = bookService.findById(id);

        // Then
        assertEquals(bookDto, result);
    }

    @Test
    @DisplayName("Verify findById() method throws exception for non-existing ID")
    void findById_NonExistingId_ThrowsEntityNotFoundException() {
        // Given
        Long id = ID_1;

        // Mocking behavior
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            bookService.findById(id);
        });

        assertEquals("Can`t find a book with id: " + id, exception.getMessage());
    }

    @Test
    @DisplayName("Verify findAll() method works")
    void findAll_ValidPageable_ReturnsListOfBookDto() {
        // Given
        Pageable pageable = Pageable.unpaged();
        List<Book> books = List.of(createBook());
        List<BookDto> bookDtos = books.stream()
                .map(TestDataUtils::getBookDto)
                .collect(Collectors.toList());

        // Mocking behavior
        when(bookRepository.findAll(pageable)).thenReturn(new PageImpl<>(books));
        when(bookMapper.toDto(any(Book.class))).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            return getBookDto(book);
        });

        // When
        List<BookDto> result = bookService.findAll(pageable);

        // Then
        assertEquals(bookDtos, result);
    }

    @Test
    @DisplayName("Verify findAllByCategoryId() method works")
    void findAllByCategoryId_ValidCategoryId_ReturnsListOfBookDtoWithoutCategoryIds() {
        // Given
        Long categoryId = ID_1;
        Pageable pageable = Pageable.unpaged();
        List<Book> books = List.of(createBook());
        List<BookDtoWithoutCategoryIds> bookDtosWithoutCategoryIds = books.stream()
                .map(TestDataUtils::getBookDtoWithoutCategories)
                .collect(Collectors.toList());

        // Mocking behavior
        when(bookRepository.findAllByCategoryId(categoryId, pageable)).thenReturn(books);
        when(bookMapper.toDtoWithoutCategories(any(Book.class))).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            return getBookDtoWithoutCategories(book);
        });

        // When
        List<BookDtoWithoutCategoryIds> result =
                bookService.findAllByCategoryId(categoryId, pageable);

        // Then
        assertEquals(bookDtosWithoutCategoryIds, result);
    }

    @Test
    @DisplayName("Verify update() method works")
    void update_ExistingIdAndValidRequestDto_ReturnsUpdatedBookDto() {
        // Given
        Long id = ID_1;
        Book book = createBook();
        book.setId(id);
        Book updatedBook = createBook();
        updatedBook.setId(id);
        BookDto updatedBookDto = getBookDto(updatedBook);
        List<Category> categories = createCategories();
        CreateBookRequestDto requestDto = createBookRequestDto();

        // Mocking behavior
        when(categoryRepository.findAll()).thenReturn(categories);
        when(bookRepository.existsById(id)).thenReturn(true);
        when(bookMapper.toModel(any(CreateBookRequestDto.class))).thenReturn(updatedBook);
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);
        when(bookMapper.toDto(any(Book.class))).thenReturn(updatedBookDto);

        // When
        BookDto result = bookService.update(id, requestDto);

        // Then
        assertEquals(updatedBookDto, result);
    }

    @Test
    @DisplayName("Verify update() method throws exception for non-existing ID")
    void update_NonExistingId_ThrowsEntityNotFoundException() {
        // Given
        Long id = ID_1;
        CreateBookRequestDto requestDto = createBookRequestDto();
        List<Category> categories = createCategories();

        // Mocking behavior
        when(categoryRepository.findAll()).thenReturn(categories);
        when(bookRepository.existsById(id)).thenReturn(false);

        // When / Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            bookService.update(id, requestDto);
        });

        assertEquals("Can`t find a book with id: " + id, exception.getMessage());
    }

    @Test
    @DisplayName("Verify deleteById() method works")
    void deleteById_ExistingId_DeletesBook() {
        // Given
        Long id = ID_1;
        Book book = createBook();

        // Mocking behavior
        when(bookRepository.existsById(id)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(id);

        // When & Then
        assertDoesNotThrow(() -> bookService.deleteById(id));
    }

    @Test
    @DisplayName("Verify deleteById() method throws exception for non-existing ID")
    void deleteById_NonExistingId_ThrowsEntityNotFoundException() {
        // Mocking behavior
        when(bookRepository.existsById(ID_1)).thenReturn(false);

        // When / Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            bookService.deleteById(ID_1);
        });

        assertEquals("Can`t find a book with id: " + ID_1, exception.getMessage());
    }
}
