package projectbookstore.service.impl;

import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbookstore.dto.cartitem.CartItemDto;
import projectbookstore.dto.cartitem.CreateCartItemRequestDto;
import projectbookstore.exception.EntityNotFoundException;
import projectbookstore.mapper.CartItemMapper;
import projectbookstore.model.CartItem;
import projectbookstore.model.ShoppingCart;
import projectbookstore.repository.BookRepository;
import projectbookstore.repository.CartItemRepository;
import projectbookstore.service.CartItemService;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartItemDto create(CreateCartItemRequestDto requestDto,
                               ShoppingCart shoppingCart,String title) {
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        cartItem.getBook().setTitle(title);
        cartItem.setShoppingCart(shoppingCart);
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public CartItemDto updateCartItem(CartItem cartItem,
                                   int quantity, ShoppingCart shoppingCart) {
        checkCartItemPresence(cartItem.getId(), shoppingCart);
        cartItem.setQuantity(quantity);
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void delete(Long id, ShoppingCart shoppingCart) {
        checkCartItemPresence(id, shoppingCart);
        cartItemRepository.deleteById(id);
    }

    private void checkCartItemPresence(Long itemId, ShoppingCart shoppingCart) {
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        boolean isPresent = cartItems.stream()
                .anyMatch(item -> item.getId().equals(itemId));
        Optional<CartItem> cartItemDB = cartItemRepository.findById(itemId);
        if (!isPresent || cartItemDB.isEmpty()) {
            throw new EntityNotFoundException(
                    "Can't find CartItem with id " + itemId);
        }
    }
}
