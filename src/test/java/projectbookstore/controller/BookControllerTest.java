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
import static projectbookstore.utils.TestDataUtils.createBookRequestDto;
import static projectbookstore.utils.TestDataUtils.createResponseDto;

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
import projectbookstore.dto.book.BookDto;
import projectbookstore.dto.book.CreateBookRequestDto;
import projectbookstore.service.BookService;
import projectbookstore.utils.TestDataUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    public static final int TIMES = 1;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

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
    @DisplayName("Create valid book")
    @WithMockUser(username = "adminUser", roles = {"ADMIN"})
    void createBook_ValidRequest_ReturnsCreatedBook() throws Exception {
        CreateBookRequestDto requestDto = createBookRequestDto();
        BookDto responseDto = createResponseDto();
        given(bookService.save(any(CreateBookRequestDto.class)))
                .willReturn(responseDto);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(
                        objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    @DisplayName("Get all books with valid role")
    @WithMockUser(username = "user", roles = {"ADMIN", "USER"})
    void getAllBooks_withValidRole_ReturnsAllBooks() throws Exception {
        List<BookDto> bookDtoList = List.of(TestDataUtils.createResponseDto());
        given(bookService.findAll(any(Pageable.class))).willReturn(bookDtoList);

        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .json(objectMapper.writeValueAsString(bookDtoList)));
    }

    @Test
    @DisplayName("Get book by ID")
    @WithMockUser(username = "user", roles = {"ADMIN", "USER"})
    void getBookById_withValidId_ReturnsBook() throws Exception {
        BookDto responseDto = TestDataUtils.createResponseDto();
        given(bookService.findById(ID_1)).willReturn(responseDto);

        mockMvc.perform(get("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .json(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    @DisplayName("Update book")
    @WithMockUser(username = "adminUser", roles = {"ADMIN"})
    void updateBook_ValidRequest_ReturnsUpdatedBook() throws Exception {
        CreateBookRequestDto requestDto = TestDataUtils.createBookRequestDto();
        BookDto responseDto = TestDataUtils.createResponseDto();
        given(bookService.update(eq(ID_1), any(CreateBookRequestDto.class)))
                .willReturn(responseDto);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .json(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    @DisplayName("Delete book by ID")
    @WithMockUser(username = "adminUser", roles = {"ADMIN"})
    void deleteBook_byId_DeletesBook() throws Exception {
        mockMvc.perform(delete("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(bookService, times(TIMES)).deleteById(ID_1);
    }
}
