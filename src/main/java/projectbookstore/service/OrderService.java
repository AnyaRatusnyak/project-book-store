package projectbookstore.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import projectbookstore.dto.order.CreateOrderRequestDto;
import projectbookstore.dto.order.OrderDto;
import projectbookstore.dto.order.UpdateOrderStatusDto;
import projectbookstore.dto.orderitem.OrderItemDto;
import projectbookstore.model.User;

public interface OrderService {
    OrderDto save(CreateOrderRequestDto requestDto, User user);

    List<OrderDto> findAllByUser(Pageable pageable, Long id);

    OrderDto update(Long id, User user, UpdateOrderStatusDto dto);

    List<OrderItemDto> findAllOrderItemsByOrderId(Pageable pageable, Long userId, Long orderId);

    OrderItemDto findOrderItemById(Long userId, Long itemId);
}
