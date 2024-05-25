package projectbookstore.service;

import projectbookstore.dto.cartitem.CartItemDto;
import projectbookstore.dto.cartitem.CreateCartItemRequestDto;
import projectbookstore.dto.cartitem.UpdateCartItemDto;
import projectbookstore.dto.shoppingcart.ShoppingCartDto;
import projectbookstore.model.User;

public interface ShoppingCartService {
    ShoppingCartDto findByUserId(User user);

    CartItemDto addBookToTheShoppingCart(CreateCartItemRequestDto requestDto, User user);

    CartItemDto updateCartItem(Long id,User user, UpdateCartItemDto requestDto);

    void deleteCartItem(Long id, User user);
}
