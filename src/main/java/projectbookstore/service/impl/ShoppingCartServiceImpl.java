package projectbookstore.service.impl;

import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbookstore.dto.book.BookDto;
import projectbookstore.dto.cartitem.CartItemDto;
import projectbookstore.dto.cartitem.CreateCartItemRequestDto;
import projectbookstore.dto.cartitem.UpdateCartItemDto;
import projectbookstore.dto.shoppingcart.ShoppingCartDto;
import projectbookstore.exception.EntityNotFoundException;
import projectbookstore.mapper.ShoppingCartMapper;
import projectbookstore.model.CartItem;
import projectbookstore.model.ShoppingCart;
import projectbookstore.model.User;
import projectbookstore.repository.CartItemRepository;
import projectbookstore.repository.ShoppingCartRepository;
import projectbookstore.service.BookService;
import projectbookstore.service.CartItemService;
import projectbookstore.service.ShoppingCartService;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemService cartItemService;
    private final BookService bookService;
    private final CartItemRepository cartItemRepository;

    @Transactional
    @Override
    public ShoppingCartDto findByUserId(User user) {
        ShoppingCart shoppingCart = findShoppingCartByUserId(user.getId());
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public CartItemDto addBookToTheShoppingCart(CreateCartItemRequestDto requestDto, User user) {
        ShoppingCart shoppingCart = findShoppingCartByUserId(user.getId());
        BookDto bookDto = bookService.findById(requestDto.getBookId());
        Optional<CartItem> cartItem = shoppingCart.getCartItems().stream()
                .filter(i -> i.getBook().getId().equals(bookDto.getId()))
                .findFirst();
        cartItem.ifPresent(item -> cartItemService.updateCartItem(item, requestDto.getQuantity()));

        return cartItemService.create(requestDto,shoppingCart);
    }

    @Override
    @Transactional
    public CartItemDto updateCartItem(Long id, User user, UpdateCartItemDto requestDto) {
        ShoppingCart shoppingCart = findShoppingCartByUserId(user.getId());
        CartItem cartItem = cartItemRepository.findById(id).get();
        checkCartItemPresence(id, shoppingCart);
        return cartItemService.updateCartItem(cartItem, requestDto.getQuantity());
    }

    @Override
    @Transactional
    public void deleteCartItem(Long id, User user) {
        ShoppingCart shoppingCart = findShoppingCartByUserId(user.getId());
        checkCartItemPresence(id, shoppingCart);
        cartItemService.delete(id);
    }

    public ShoppingCart findShoppingCartByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can`t find shopping cart by user id: " + userId));
    }

    private void checkCartItemPresence(Long itemId, ShoppingCart shoppingCart) {
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        boolean isPresent = cartItems.stream()
                .anyMatch(item -> item.getId().equals(itemId));
        if (!isPresent) {
            throw new EntityNotFoundException(
                    "Can't find CartItem with id " + itemId + " in your shopping cart");
        }
    }
}
