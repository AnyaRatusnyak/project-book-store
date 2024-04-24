package projectbookstore.mapper;

import org.mapstruct.Mapper;
import projectbookstore.config.MapperConfig;
import projectbookstore.dto.BookDto;
import projectbookstore.dto.CreateBookRequestDto;
import projectbookstore.model.Book;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
