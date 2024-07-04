package projectbookstore.dto.book;

import java.math.BigDecimal;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDtoWithoutCategoryIds {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private BigDecimal price;
    private String description;
    private String coverImage;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookDtoWithoutCategoryIds bookDto = (BookDtoWithoutCategoryIds) o;
        return Objects.equals(id, bookDto.id)
                && Objects.equals(title, bookDto.title)
                && Objects.equals(author, bookDto.author)
                && Objects.equals(isbn, bookDto.isbn)
                && Objects.equals(price, bookDto.price)
                && Objects.equals(description, bookDto.description)
                && Objects.equals(coverImage, bookDto.coverImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, isbn, price, description, coverImage);
    }
}


