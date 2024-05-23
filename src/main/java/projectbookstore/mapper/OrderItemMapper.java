package projectbookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import projectbookstore.config.MapperConfig;
import projectbookstore.dto.orderitem.OrderItemDto;
import projectbookstore.model.OrderItem;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book.id")
   OrderItemDto toDto(OrderItem orderItem);
}
