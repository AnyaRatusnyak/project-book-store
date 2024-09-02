package projectbookstore.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static projectbookstore.utils.TestDataUtils.ID_1;
import static projectbookstore.utils.TestDataUtils.createCategory;
import static projectbookstore.utils.TestDataUtils.createCategoryRequestDto;
import static projectbookstore.utils.TestDataUtils.getCategoryDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import projectbookstore.dto.category.CategoryDto;
import projectbookstore.dto.category.CreateCategoryRequestDto;
import projectbookstore.exception.EntityNotFoundException;
import projectbookstore.mapper.CategoryMapper;
import projectbookstore.model.Category;
import projectbookstore.repository.CategoryRepository;
import projectbookstore.utils.TestDataUtils;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Verify save() method works")
    void save_CreateCategoryRequestDto_ReturnsCategoryDto() {
        // Given
        CreateCategoryRequestDto requestDto = createCategoryRequestDto();
        Category category = createCategory();
        CategoryDto categoryDto = getCategoryDto(category);

        // Mocking behavior
        when(categoryMapper.toModel(any(CreateCategoryRequestDto.class))).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toDto(any(Category.class))).thenReturn(categoryDto);

        // When
        CategoryDto result = categoryService.save(requestDto);

        // Then
        assertEquals(getCategoryDto(category), result);
    }

    @Test
    @DisplayName("Verify findById() method works")
    void findById_ExistingId_ReturnsCategoryDto() {
        // Given
        Long id = ID_1;
        Category category = createCategory();
        CategoryDto categoryDto = getCategoryDto(category);

        // Mocking behavior
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        // When
        CategoryDto result = categoryService.findById(id);

        // Then
        assertEquals(categoryDto, result);
    }

    @Test
    @DisplayName("Verify findById() method throws exception for non-existing ID")
    void findById_NonExistingId_ThrowsEntityNotFoundException() {
        // Given
        Long id = ID_1;

        // Mocking behavior
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            categoryService.findById(id);
        });

        assertEquals("Can`t find a category with id: " + id, exception.getMessage());
    }

    @Test
    @DisplayName("Verify findAll() method works")
    void findAll_ValidPageable_ReturnsListOfCategoryDto() {
        // Given
        Pageable pageable = Pageable.unpaged();
        List<Category> categories = List.of(createCategory());
        List<CategoryDto> categoryDtos = categories.stream()
                .map(TestDataUtils::getCategoryDto)
                .collect(Collectors.toList());

        // Mocking behavior
        when(categoryRepository.findAll(pageable)).thenReturn(new PageImpl<>(categories));
        when(categoryMapper.toDto(any(Category.class))).thenAnswer(invocation -> {
            Category category = invocation.getArgument(0);
            return getCategoryDto(category);
        });

        // When
        List<CategoryDto> result = categoryService.findAll(pageable);

        // Then
        assertEquals(categoryDtos, result);
    }

    @Test
    @DisplayName("Verify update() method works")
    void update_ExistingIdAndValidRequestDto_ReturnsUpdatedCategoryDto() {
        // Given
        Long id = ID_1;
        Category category = createCategory();
        category.setId(id);
        Category updatedCategory = createCategory();
        updatedCategory.setId(id);
        CategoryDto updatedCategoryDto = getCategoryDto(updatedCategory);
        CreateCategoryRequestDto requestDto = createCategoryRequestDto();

        // Mocking behavior
        when(categoryRepository.existsById(id)).thenReturn(true);
        when(categoryMapper.toModel(any(CreateCategoryRequestDto.class)))
                .thenReturn(updatedCategory);
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);
        when(categoryMapper.toDto(any(Category.class))).thenReturn(updatedCategoryDto);

        // When
        CategoryDto result = categoryService.update(id, requestDto);

        // Then
        assertEquals(updatedCategoryDto, result);
    }

    @Test
    @DisplayName("Verify update() method throws exception for non-existing ID")
    void update_NonExistingId_ThrowsEntityNotFoundException() {
        // Given
        Long id = ID_1;
        CreateCategoryRequestDto requestDto = createCategoryRequestDto();

        // Mocking behavior
        when(categoryRepository.existsById(id)).thenReturn(false);

        // When / Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            categoryService.update(id, requestDto);
        });

        assertEquals("Can`t find a category with id: " + id, exception.getMessage());
    }

    @Test
    @DisplayName("Verify deleteById() method works")
    void deleteById_ExistingId_DeletesCategory() {
        // Given
        Long id = ID_1;

        // Mocking behavior
        when(categoryRepository.existsById(id)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(id);

        // When & Then
        assertDoesNotThrow(() -> categoryService.deleteById(id));
    }

    @Test
    @DisplayName("Verify deleteById() method throws exception for non-existing ID")
    void deleteById_NonExistingId_ThrowsEntityNotFoundException() {
        // Given
        Long id = ID_1;

        // Mocking behavior
        when(categoryRepository.existsById(id)).thenReturn(false);

        // When / Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            categoryService.deleteById(id);
        });

        assertEquals("Can`t find a category with id: " + id, exception.getMessage());
    }
}
