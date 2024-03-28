package book.store.onlinebookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import book.store.onlinebookstore.dto.BookDto;
import book.store.onlinebookstore.dto.BookSearchParametersDto;
import book.store.onlinebookstore.dto.CreateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("""
            Create new book
            """)
    @Sql(scripts = "classpath:database/books/delete-book-from-db-after-save.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createBook_ValidRequestDto_Success() throws Exception {
        //given
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto()
                .setTitle("Lisova Mavka")
                .setAuthor("Lesya Ukrainka")
                .setIsbn("43623uefhef723")
                .setPrice(BigDecimal.valueOf(50))
                .setDescription("Awesome")
                .setCoverImage("SuperImage");
        BookDto expected = new BookDto()
                .setId(1L)
                .setTitle("Lisova Mavka")
                .setAuthor("Lesya Ukrainka")
                .setIsbn("43623uefhef723")
                .setPrice(BigDecimal.valueOf(50))
                .setDescription("Awesome")
                .setCoverImage("SuperImage");
        String jsonRequest = objectMapper.writeValueAsString(createBookRequestDto);
        //when
        MvcResult result = mockMvc.perform(post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //then
        BookDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), BookDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(actual.getTitle(), expected.getTitle());
        Assertions.assertEquals(actual.getAuthor(), expected.getAuthor());
    }

    @Test
    @Sql(scripts = "classpath:database/books/add-two-books-to-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-two-books-from-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("""
            Get all books
            """)
    public void getAll_ReturnAllBooks_Success() throws Exception {
        //given
        BookDto expectedFirst = new BookDto()
                .setId(1L)
                .setTitle("Kobzar")
                .setAuthor("Shevchenko")
                .setIsbn("43623uefhef723")
                .setPrice(BigDecimal.valueOf(50))
                .setDescription("Awesome")
                .setCoverImage("SuperImage");
        BookDto expectedSecond = new BookDto()
                .setId(2L)
                .setTitle("Lisova Mavka")
                .setAuthor("LesiyaUkrainka")
                .setIsbn("3i32h3348wjew")
                .setPrice(BigDecimal.valueOf(50))
                .setDescription("Cool")
                .setCoverImage("Big Image");
        //when
        MvcResult result = mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDto> actualList = Arrays.asList(objectMapper
                .readValue(result.getResponse().getContentAsString(), BookDto[].class));
        //then
        Assertions.assertFalse(actualList.isEmpty());
        BookDto actualFirstBook = actualList.get(0);
        BookDto actualSecondBook = actualList.get(1);
        EqualsBuilder.reflectionEquals(expectedFirst, actualFirstBook, "id");
        EqualsBuilder.reflectionEquals(expectedSecond, actualSecondBook, "id");
    }

    @Test
    @Sql(scripts = "classpath:database/books/add-one-book-to-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-one-book-from-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("""
            Get book by id
            """)
    public void getBookById_ReturnCorrectBook_Success() throws Exception {
        //given
        Long bookId = 1L;
        BookDto expected = new BookDto()
                .setId(1L)
                .setTitle("Kobzar")
                .setAuthor("Shevchenko")
                .setIsbn("43623uefhef723")
                .setPrice(BigDecimal.valueOf(50))
                .setDescription("Awesome")
                .setCoverImage("SuperImage");
        //when
        MvcResult result = mockMvc.perform(get("/books/{id}", bookId))
                .andExpect(status().isOk())
                .andReturn();
        BookDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), BookDto.class);
        //then
        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(actual, expected, "id");
    }

    @Test
    @Sql(scripts = "classpath:database/books/add-one-book-to-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-one-book-from-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("""
            Search books by parameter
            """)
    public void search_ReturnCorrectSearchedBook_Success() throws Exception {
        //given
        BookDto expected = new BookDto()
                .setId(1L)
                .setTitle("Kobzar")
                .setAuthor("Shevchenko")
                .setIsbn("43623uefhef723")
                .setPrice(BigDecimal.valueOf(50))
                .setDescription("Awesome")
                .setCoverImage("SuperImage");
        BookSearchParametersDto bookSearchParametersDto
                = new BookSearchParametersDto(
                        new String[]{"Kobzar"},
                        new String[]{""},
                        new String[]{""},
                        new String[]{""});
        String jsonRequest = objectMapper.writeValueAsString(bookSearchParametersDto);
        //when
        MvcResult result = mockMvc.perform(get("/books/search")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //then
        List<BookDto> expectedList = Arrays.asList(objectMapper
                .readValue(result.getResponse().getContentAsString(), BookDto[].class));
        Assertions.assertFalse(expectedList.isEmpty());
        BookDto actual = expectedList.get(0);
        EqualsBuilder.reflectionEquals(actual, expected);
    }

    @Test
    @Sql(scripts = "classpath:database/books/add-one-book-to-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-updated-book-in-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("""
            Update book by id
            """)
    public void updateBookById_CorrectUpdateBook_Success() throws Exception {
        //given
        Long bookId = 1L;
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto()
                .setTitle("Lisova Mavka")
                .setAuthor("LesiyaUkrainka")
                .setIsbn("3i32h3348wjew")
                .setPrice(BigDecimal.valueOf(50))
                .setDescription("Cool")
                .setCoverImage("Big Image");
        BookDto expected = new BookDto()
                .setId(1L)
                .setTitle("Lisova Mavka")
                .setAuthor("LesiyaUkrainka")
                .setIsbn("3i32h3348wjew")
                .setPrice(BigDecimal.valueOf(50))
                .setDescription("Cool")
                .setCoverImage("Big Image");
        String jsonRequest = objectMapper.writeValueAsString(createBookRequestDto);
        //when
        MvcResult result = mockMvc.perform(put("/books/{id}", bookId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        BookDto actual = objectMapper.readValue(result
                .getResponse().getContentAsString(), BookDto.class);
        //then
        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(actual, expected);
    }
}
