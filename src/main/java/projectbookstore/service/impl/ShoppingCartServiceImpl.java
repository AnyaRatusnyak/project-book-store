package projectbookstore.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    @Override
    public ShoppingCartDto findByUserId(User user) {
        ShoppingCart shoppingCart = getExistedCart(user.getId());
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public CartItemDto addBookToTheShoppingCart(CreateCartItemRequestDto requestDto, User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseGet(() -> shoppingCartRepository.save(new ShoppingCart(user)));
        BookDto bookDto = bookService.findById(requestDto.getBookId());
        Optional<CartItem> cartItem = shoppingCart.getCartItems().stream()
                .filter(i -> i.getBook().getId().equals(bookDto.getId()))
                .findFirst();
        cartItem.ifPresent(item ->
                cartItemService.updateCartItem(item, requestDto.getQuantity(), shoppingCart));

        return cartItemService.create(requestDto,shoppingCart);
    }

    @Override
    @Transactional
    public CartItemDto updateCartItem(Long id, User user, UpdateCartItemDto requestDto) {
        ShoppingCart shoppingCart = getExistedCart(user.getId());
        CartItem cartItem = cartItemRepository.findById(id).get();
        return cartItemService.updateCartItem(cartItem, requestDto.getQuantity(), shoppingCart);
    }

    @Override
    @Transactional
    public void deleteCartItem(Long id, User user) {
        ShoppingCart shoppingCart = getExistedCart(user.getId());
        cartItemService.delete(id, shoppingCart);
    }

    @Override
    public ShoppingCart getExistedCart(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can`t find shopping cart by user id: " + userId));
    }
}
