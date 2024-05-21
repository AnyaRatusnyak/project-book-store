package projectbookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbookstore.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
