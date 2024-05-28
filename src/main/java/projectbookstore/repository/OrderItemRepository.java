package projectbookstore.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projectbookstore.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

    @Query("FROM OrderItem oi JOIN oi.order o WHERE oi.id = "
            + ":itemId AND o.user.id = :userId ")
    Optional<OrderItem> findByIdAndUserId(Long itemId,Long userId);
}
