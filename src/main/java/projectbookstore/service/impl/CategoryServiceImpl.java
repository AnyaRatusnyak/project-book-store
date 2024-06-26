package projectbookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import projectbookstore.dto.category.CategoryDto;
import projectbookstore.dto.category.CreateCategoryRequestDto;
import projectbookstore.exception.EntityNotFoundException;
import projectbookstore.mapper.CategoryMapper;
import projectbookstore.model.Category;
import projectbookstore.repository.CategoryRepository;
import projectbookstore.service.CategoryService;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto save(CreateCategoryRequestDto requestDto) {
        Category category = categoryMapper.toModel(requestDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can`t find a category with id: " + id));

        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryRequestDto requestDto) {
        checkCategoryExists(id);
        Category updateCategory = categoryMapper.toModel(requestDto);
        updateCategory.setId(id);
        return categoryMapper.toDto(categoryRepository.save(updateCategory));
    }

    @Override
    public void deleteById(Long id) {
        checkCategoryExists(id);
        categoryRepository.deleteById(id);
    }

    private void checkCategoryExists(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Can`t find a category with id: " + id);
        }
    }
}
