package projectbookstore.dto.cartitem;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Long bookId;
    private int quantity;
}
