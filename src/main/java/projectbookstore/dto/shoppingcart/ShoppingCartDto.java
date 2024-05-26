package projectbookstore.dto.shoppingcart;

import java.util.Set;
import lombok.Data;
import projectbookstore.dto.cartitem.CartItemDto;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItemDto> cartItems;
}
