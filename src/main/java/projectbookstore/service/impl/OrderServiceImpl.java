package projectbookstore.service.impl;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import projectbookstore.dto.order.CreateOrderRequestDto;
import projectbookstore.dto.order.OrderDto;
import projectbookstore.dto.order.UpdateOrderStatusDto;
import projectbookstore.dto.orderitem.OrderItemDto;
import projectbookstore.exception.EntityNotFoundException;
import projectbookstore.mapper.OrderItemMapper;
import projectbookstore.mapper.OrderMapper;
import projectbookstore.model.CartItem;
import projectbookstore.model.Order;
import projectbookstore.model.OrderItem;
import projectbookstore.model.ShoppingCart;
import projectbookstore.model.User;
import projectbookstore.repository.OrderItemRepository;
import projectbookstore.repository.OrderRepository;
import projectbookstore.repository.ShoppingCartRepository;
import projectbookstore.service.OrderService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Transactional
    @Override
    public OrderDto save(CreateOrderRequestDto requestDto, User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can`t find shopping cart by "
                + "user id: " + user.getId()));
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.getShippingAddress());
        BigDecimal total = calculateTotalPrice(shoppingCart.getCartItems());
        order.setTotal(total);
        Order savedOrder = orderRepository.save(order);
        Set<OrderItem> orderItems = createOrderItems(savedOrder, shoppingCart);
        savedOrder.setOrderItems(orderItems);
        shoppingCartRepository.deleteById(shoppingCart.getId());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Transactional
    @Override
    public List<OrderDto> findAllByUser(Pageable pageable, Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(pageable,userId);
        List<OrderDto> dtos = orders.stream()
                .map(orderMapper::toDto)
                .toList();
        return dtos;
    }

    @Override
    public List<OrderItemDto> findAllOrderItemsByOrderId(
            Pageable pageable, Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException("Can`t find an order by "
                + "order id: " + orderId));
        if (! order.getUser().getId().equals(userId)) {
            throw new EntityNotFoundException("Can't find an order by order id: "
                    + orderId + " for user id: " + userId);
        }
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(pageable,orderId);
        return orderItems.stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto findOrderItemById(Long userId, Long itemId) {
        OrderItem orderItem = orderItemRepository.findByIdAndUserId(itemId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Can`t find an orderItem by "
                + "orderItem id: " + itemId));
        return orderItemMapper.toDto(orderItem);
    }

    @Override
    public OrderDto update(Long id, User user, UpdateOrderStatusDto dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can`t find an order by "
                + "order id: " + id));
        order.setStatus(dto.getStatus());
        return orderMapper.toDto(orderRepository.save(order));
    }

    private BigDecimal calculateTotalPrice(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(item -> item.getBook().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Set<OrderItem> createOrderItems(Order order, ShoppingCart shoppingCart) {
        Set<OrderItem> orderItems = shoppingCart.getCartItems().stream()
                .map(item -> createOrderItem(item, order))
                .collect(Collectors.toSet());
        orderItemRepository.saveAll(orderItems);
        return orderItems;
    }

    private OrderItem createOrderItem(CartItem cartItem, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getBook().getPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        return orderItem;
    }

}
