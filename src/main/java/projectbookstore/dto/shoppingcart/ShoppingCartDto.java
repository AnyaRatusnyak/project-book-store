package projectbookstore.dto.shoppingcart;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import projectbookstore.dto.cartitem.CartItemDto;

@Getter
@Setter
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItemDto> cartItems;
}
