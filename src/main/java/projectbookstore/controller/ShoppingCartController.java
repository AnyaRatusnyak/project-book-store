package projectbookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import projectbookstore.dto.cartitem.CartItemDto;
import projectbookstore.dto.cartitem.CreateCartItemRequestDto;
import projectbookstore.dto.cartitem.UpdateCartItemDto;
import projectbookstore.dto.shoppingcart.ShoppingCartDto;
import projectbookstore.model.User;
import projectbookstore.service.ShoppingCartService;

@Tag(name = "Shopping carts management", description = "Endpoints for managing shopping carts")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get the shopping cart", description = "Get the shopping cart")
    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        return shoppingCartService.findByUserId(user);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    @Operation(summary = "Add book to the shopping cart",
            description = "Add book to the shopping cart")
    public CartItemDto addBookToTheShoppingCart(
            @Valid @RequestBody CreateCartItemRequestDto requestDto,
                                                Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        return shoppingCartService.addBookToTheShoppingCart(requestDto, user);
    }

    @PutMapping("/cart-items/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Update book's quantity in the shopping cart",
            description = "Update book's quantity in the shopping cart")
    public CartItemDto updateQuantityofBook(@PathVariable Long id,
                                      @Valid @RequestBody UpdateCartItemDto requestDto,
                                      Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.updateCartItem(id, user, requestDto);
    }

    @DeleteMapping("/cart-items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Remove current book from the shopping cart",
            description = "Remove current book from the shopping cart")
    public void deleteBook(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.deleteCartItem(id, user);
    }
}
