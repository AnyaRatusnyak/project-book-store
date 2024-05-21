package projectbookstore.dto.cartitem;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemDto {
    @Min(1)
    private int quantity;
}
