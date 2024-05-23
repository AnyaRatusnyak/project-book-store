package projectbookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import projectbookstore.dto.order.CreateOrderRequestDto;
import projectbookstore.dto.order.OrderDto;
import projectbookstore.dto.order.UpdateOrderStatusDto;
import projectbookstore.dto.orderitem.OrderItemDto;
import projectbookstore.model.User;
import projectbookstore.service.OrderService;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @Operation(summary = "Get user's order history", description = "Get user's order history")
    public List<OrderDto> getHistoryOfOrders(Authentication authentication, Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.findAllByUser(pageable, user.getId());
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @Operation(summary = "Get all OrderItems for the order with current id",
            description = "Get all OrderItems for the order with current id")
    public List<OrderItemDto> getAllOrderItems(
            @PathVariable Long orderId,
            Authentication authentication,
            Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.findAllOrderItemsByOrderId(pageable, user.getId(), orderId);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Create a new order", description = "Create a new order")
    public OrderDto createOrder(
            Authentication authentication,
            @Valid @RequestBody CreateOrderRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return orderService.save(requestDto, user);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PatchMapping("/{orderId}")
    @Operation(summary = "Update status of  the order with current id",
            description = "Update status of  the order with current id")
    public OrderDto updateStatus(
            @PathVariable Long orderId,
            Authentication authentication,
            @Valid @RequestBody UpdateOrderStatusDto dto) {
        User user = (User) authentication.getPrincipal();
        return orderService.update(orderId, user, dto);
    }

    @Operation(summary = "Get the OrderItem from the order with current id",
            description = "Get the OrderItem from the order with current id")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemDto getOrderItem(@PathVariable Long itemId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.findOrderItemById(user.getId(), itemId);
    }
}
