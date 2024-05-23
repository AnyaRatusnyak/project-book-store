package projectbookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import projectbookstore.config.MapperConfig;
import projectbookstore.dto.order.OrderDto;
import projectbookstore.model.Order;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    OrderDto toDto(Order order);
}
