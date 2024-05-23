package projectbookstore.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectbookstore.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    @Query("FROM OrderItem oi  WHERE oi.order.id = :orderId")
    List<OrderItem> findAllByOrderId(Pageable pageable, @Param("orderId") Long orderId);

    @Query("FROM OrderItem oi JOIN oi.order o WHERE oi.id = "
            + ":itemId AND o.user.id = :userId ")
    Optional<OrderItem> findByIdAndUserId(@Param("itemId") Long itemId,
                                          @Param("userId") Long userId);
}
