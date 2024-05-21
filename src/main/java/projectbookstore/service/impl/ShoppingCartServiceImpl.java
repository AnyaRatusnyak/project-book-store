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
        Long userId = user.getId();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseGet(() -> shoppingCartRepository.save(new ShoppingCart(user)));
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public CartItemDto addBookToTheShoppingCart(CreateCartItemRequestDto requestDto, User user) {
        Long userId = user.getId();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseGet(() -> shoppingCartRepository.save(new ShoppingCart(user)));
        BookDto bookDto = bookService.findById(requestDto.getBookId());
        Optional<CartItem> cartItem = shoppingCart.getCartItems().stream()
                .filter(i -> i.getBook().getTitle().equals(bookDto.getTitle()))
                .findFirst();
        if (cartItem.isPresent()) {
            cartItemService.updateCartItem(cartItem.get(),requestDto.getQuantity());
        }

        return cartItemService.create(requestDto,bookDto.getTitle(),shoppingCart);
    }

    @Override
    @Transactional
    public CartItemDto updateCartItem(Long id, User user, UpdateCartItemDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseGet(() -> shoppingCartRepository.save(new ShoppingCart(user)));
        CartItem cartItem = cartItemRepository.findById(id).get();
        return cartItemService.updateCartItem(cartItem, requestDto.getQuantity());
    }

    @Override
    @Transactional
    public void deleteCartItem(Long id, User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can`t find shopping cart by "
                + "user id: " + user.getId()));
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        boolean isPresent = cartItems.stream()
                .anyMatch(item -> item.getId().equals(id));
        if (!isPresent) {
            throw new EntityNotFoundException("Can't find CartItem with id " + id
                    + " not in your shopping cart");
        }
        cartItemService.delete(id);
    }
}
