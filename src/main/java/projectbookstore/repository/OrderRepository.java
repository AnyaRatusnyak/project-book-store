package projectbookstore.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projectbookstore.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByUserId(Pageable pageable, Long id);

    @Query("FROM Order o JOIN o.user u WHERE o.id = "
            + ":orderId AND o.user.id = :userId ")
   Optional<Order> findByOrderIdAndUserId(Long orderId, Long userId);
}
