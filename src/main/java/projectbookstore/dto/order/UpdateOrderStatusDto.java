package projectbookstore.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import projectbookstore.model.Order;

@Data
public class UpdateOrderStatusDto {
    @NotNull
    private Order.Status status;
}
