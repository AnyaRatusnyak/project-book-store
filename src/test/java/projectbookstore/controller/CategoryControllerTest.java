package projectbookstore.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static projectbookstore.utils.TestDataUtils.ID_1;
import static projectbookstore.utils.TestDataUtils.createCategoryRequestDto;
import static projectbookstore.utils.TestDataUtils.createCategoryResponseDto;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import projectbookstore.dto.category.CategoryDto;
import projectbookstore.dto.category.CreateCategoryRequestDto;
import projectbookstore.service.BookService;
import projectbookstore.service.CategoryService;
import projectbookstore.utils.TestDataUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {
    public static final int TIMES = 1;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private BookService bookService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Create valid category")
    @WithMockUser(username = "adminUser", roles = {"ADMIN"})
    void createCategory_ValidRequest_ReturnsCreatedCategory() throws Exception {
        CreateCategoryRequestDto requestDto = createCategoryRequestDto();
        CategoryDto responseDto = createCategoryResponseDto();
        given(categoryService.save(any(CreateCategoryRequestDto.class)))
                .willReturn(responseDto);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(
                        objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    @DisplayName("Get all categories with valid role")
    @WithMockUser(username = "user", roles = {"ADMIN", "USER"})
    void getAllCategories_withValidRole_ReturnsAllCategories() throws Exception {
        List<CategoryDto> categoryDtoList = List.of(TestDataUtils.createCategoryResponseDto());
        given(categoryService.findAll(any(Pageable.class))).willReturn(categoryDtoList);

        mockMvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .json(objectMapper.writeValueAsString(categoryDtoList)));
    }

    @Test
    @DisplayName("Get category by ID")
    @WithMockUser(username = "user", roles = {"ADMIN", "USER"})
    void getCategoryById_withValidId_ReturnsCategory() throws Exception {
        CategoryDto responseDto = TestDataUtils.createCategoryResponseDto();
        given(categoryService.findById(ID_1)).willReturn(responseDto);

        mockMvc.perform(get("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .json(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    @DisplayName("Update category")
    @WithMockUser(username = "adminUser", roles = {"ADMIN"})
    void updateCategory_ValidRequest_ReturnsUpdatedCategory() throws Exception {
        CreateCategoryRequestDto requestDto = TestDataUtils.createCategoryRequestDto();
        CategoryDto responseDto = TestDataUtils.createCategoryResponseDto();
        given(categoryService.update(eq(ID_1), any(CreateCategoryRequestDto.class)))
                .willReturn(responseDto);

        mockMvc.perform(put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .json(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    @DisplayName("Delete category by ID")
    @WithMockUser(username = "adminUser", roles = {"ADMIN"})
    void deleteCategory_byId_DeletesCategory() throws Exception {
        mockMvc.perform(delete("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(categoryService, times(TIMES)).deleteById(ID_1);
    }
}
