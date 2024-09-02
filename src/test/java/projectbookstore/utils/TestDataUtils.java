package projectbookstore.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import projectbookstore.dto.book.BookDto;
import projectbookstore.dto.book.BookDtoWithoutCategoryIds;
import projectbookstore.dto.book.CreateBookRequestDto;
import projectbookstore.dto.category.CategoryDto;
import projectbookstore.dto.category.CreateCategoryRequestDto;
import projectbookstore.model.Book;
import projectbookstore.model.Category;

public class TestDataUtils {

    public static final Long ID_1 = 1L;
    public static final Long ID_2 = 2L;
    public static final String TEST_CATEGORY = "Fiction";
    public static final String TEST_CATEGORY_DESCRIPTION = "Fiction book";
    public static final String TEST_TITLE = "Test Book";
    public static final String TEST_AUTHOR = "Test book's author";
    public static final String TEST_ISBN = "92951231556151221";
    public static final BigDecimal TEST_PRICE = BigDecimal.valueOf(200);
    public static final Set<Long> TEST_CATEGORY_IDS = Set.of(ID_1, ID_2);
    public static final String TEST_BOOK_DESCRIPTION = "description";
    public static final String TEST_BOOK_IMAGE = "image";

    public static Book createBook() {
        Book book = new Book();
        book.setTitle(TEST_TITLE);
        book.setAuthor(TEST_AUTHOR);
        book.setIsbn(TEST_ISBN);
        book.setPrice(TEST_PRICE);
        book.setDescription(TEST_BOOK_DESCRIPTION);
        book.setCoverImage(TEST_BOOK_IMAGE);
        book.setCategories(getCategories());
        return book;
    }

    public static Set<Category> getCategories() {
        return TEST_CATEGORY_IDS.stream().map(Category::new).collect(Collectors.toSet());
    }

    public static List<Category> createCategories() {
        return TEST_CATEGORY_IDS.stream().map(Category::new).collect(Collectors.toList());
    }

    public static CreateBookRequestDto createBookRequestDto() {
        CreateBookRequestDto bookRequestDto = new CreateBookRequestDto();
        bookRequestDto.setTitle(TEST_TITLE);
        bookRequestDto.setAuthor(TEST_AUTHOR);
        bookRequestDto.setIsbn(TEST_ISBN);
        bookRequestDto.setPrice(TEST_PRICE);
        bookRequestDto.setDescription(TEST_BOOK_DESCRIPTION);
        bookRequestDto.setCoverImage(TEST_BOOK_IMAGE);
        bookRequestDto.setCategoryIds(TEST_CATEGORY_IDS);
        return bookRequestDto;
    }

    public static BookDto createResponseDto() {
        BookDto bookDto = new BookDto();
        bookDto.setId(ID_1);
        bookDto.setTitle(TEST_TITLE);
        bookDto.setAuthor(TEST_AUTHOR);
        bookDto.setIsbn(TEST_ISBN);
        bookDto.setPrice(TEST_PRICE);
        bookDto.setDescription(TEST_BOOK_DESCRIPTION);
        bookDto.setCoverImage(TEST_BOOK_IMAGE);
        bookDto.setCategoryIds(TEST_CATEGORY_IDS);
        return bookDto;

    }

    public static BookDto getBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setPrice(book.getPrice());
        bookDto.setDescription(book.getDescription());
        bookDto.setCoverImage(book.getCoverImage());
        bookDto.setCategoryIds(book.getCategories().stream()
                              .map(Category::getId)
                              .collect(Collectors.toSet()));
        return bookDto;
    }

    public static BookDtoWithoutCategoryIds getBookDtoWithoutCategories(Book book) {
        BookDtoWithoutCategoryIds bookDto = new BookDtoWithoutCategoryIds();
        bookDto.setId(book.getId());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setPrice(book.getPrice());
        bookDto.setDescription(book.getDescription());
        bookDto.setCoverImage(book.getCoverImage());
        return bookDto;
    }

    public static CreateCategoryRequestDto createCategoryRequestDto() {
        CreateCategoryRequestDto categoryRequestDto = new CreateCategoryRequestDto();
        categoryRequestDto.setName(TEST_CATEGORY);
        categoryRequestDto.setDescription(TEST_CATEGORY_DESCRIPTION);
        return categoryRequestDto;
    }

    public static Category createCategory() {
        Category category = new Category();
        category.setName(TEST_CATEGORY);
        category.setDescription(TEST_CATEGORY_DESCRIPTION);
        return category;
    }

    public static CategoryDto createCategoryResponseDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(ID_1);
        categoryDto.setName(TEST_CATEGORY);
        categoryDto.setDescription(TEST_CATEGORY_DESCRIPTION);
        return categoryDto;
    }

    public static CategoryDto getCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        return categoryDto;
    }
}
