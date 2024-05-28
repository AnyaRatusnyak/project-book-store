package projectbookstore.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectbookstore.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("FROM Order o JOIN FETCH o.orderItems WHERE o.user.id = :id")
    List<Order> findAllByUserId(Pageable pageable, Long id);

    @Query("FROM Order o JOIN o.user u WHERE o.id = "
            + ":orderId AND o.user.id = :userId ")
   Optional<Order> findByOrderIdAndUserId(@Param("orderId")Long orderId,
                                         @Param("userId")Long userId);
}
