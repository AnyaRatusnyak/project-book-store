package projectbookstore.service;

import projectbookstore.dto.cartitem.CartItemDto;
import projectbookstore.dto.cartitem.CreateCartItemRequestDto;
import projectbookstore.model.CartItem;
import projectbookstore.model.ShoppingCart;

public interface CartItemService {
    CartItemDto create(CreateCartItemRequestDto requestDto,
                       ShoppingCart shoppingCart);

    CartItemDto updateCartItem(CartItem cartItem, int quantity);

    void delete(Long id);
}
