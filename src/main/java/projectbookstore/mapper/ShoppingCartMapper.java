package projectbookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import projectbookstore.config.MapperConfig;
import projectbookstore.dto.shoppingcart.ShoppingCartDto;
import projectbookstore.model.ShoppingCart;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "user.id")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
