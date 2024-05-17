package projectbookstore.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import projectbookstore.config.MapperConfig;
import projectbookstore.dto.book.BookDto;
import projectbookstore.dto.book.BookDtoWithoutCategoryIds;
import projectbookstore.dto.book.CreateBookRequestDto;
import projectbookstore.dto.book.BookDto;
import projectbookstore.dto.book.CreateBookRequestDto;
import projectbookstore.model.Book;
import projectbookstore.model.Category;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto dto, Book book) {
        Set<Long> categoryIds = book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        dto.setCategoryIds(categoryIds);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", source = "categoryIds", qualifiedByName = "categoryById")
    Book toModel(CreateBookRequestDto requestDto);

    @Named("categoryById")
    default Set<Category> categoryById(Set<Long> categoryIds) {
        return categoryIds.stream()
                .map(Category::new)
                .collect(Collectors.toSet());
    }
}
