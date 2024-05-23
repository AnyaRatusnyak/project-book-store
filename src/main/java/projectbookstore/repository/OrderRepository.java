package projectbookstore.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projectbookstore.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("FROM Order o JOIN FETCH o.orderItems WHERE o.user.id = :id")
    List<Order> findAllByUserId(Pageable pageable, Long id);
}
