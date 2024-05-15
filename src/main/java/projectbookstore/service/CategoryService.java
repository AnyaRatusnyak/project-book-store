package projectbookstore.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import projectbookstore.dto.category.CategoryDto;
import projectbookstore.dto.category.CreateCategoryRequestDto;

public interface CategoryService {
    CategoryDto save(CreateCategoryRequestDto requestDto);

    List<CategoryDto> findAll(Pageable pageable);
}
