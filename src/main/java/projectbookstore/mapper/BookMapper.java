package projectbookstore.mapper;

import org.mapstruct.Mapper;
import projectbookstore.config.MapperConfig;
import projectbookstore.dto.book.BookDto;
import projectbookstore.dto.book.CreateBookRequestDto;
import projectbookstore.model.Book;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
