package projectbookstore.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectbookstore.model.Book;

public interface BookRepository extends JpaRepository<Book,Long> {
    @Query(value = "SELECT b FROM Book b "
            + "JOIN b.categories c "
            + "WHERE c.id = :categoryId")
    List<Book> findAllByCategoryId(
            @Param("categoryId")Long categoryId, Pageable pageable);
}
