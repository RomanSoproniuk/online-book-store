package book.store.onlinebookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import book.store.onlinebookstore.dto.BookDtoWithoutCategoryIds;
import book.store.onlinebookstore.dto.CategoryDto;
import book.store.onlinebookstore.dto.CreateRequestCategoryDto;
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
public class CategoryControllerTest {
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
    @Sql(scripts = "classpath:database/books/add-two-book-category-controller.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-two-books-from-db-category-controller.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("""
            Get all books by category
            """)
    public void getBooksByCategoryId_ReturnCorrectBooks_Success() throws Exception {
        //given
        Long categoryId = 1L;
        BookDtoWithoutCategoryIds expectedFirst = new BookDtoWithoutCategoryIds()
                .setId(1L)
                .setTitle("Mavka")
                .setAuthor("Lesya")
                .setPrice(BigDecimal.valueOf(50))
                .setDescription("Cool")
                .setIsbn("12345678")
                .setCoverImage("Image1");
        BookDtoWithoutCategoryIds expectedSecond = new BookDtoWithoutCategoryIds()
                .setId(2L)
                .setTitle("Kobzar")
                .setAuthor("Taras")
                .setPrice(BigDecimal.valueOf(50))
                .setDescription("Super")
                .setIsbn("87654321")
                .setCoverImage("Image2");
        //when
        MvcResult result = mockMvc.perform(get("/categories/{id}/books", categoryId))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDtoWithoutCategoryIds> actualList = Arrays.asList(objectMapper
                .readValue(result.getResponse()
                        .getContentAsString(), BookDtoWithoutCategoryIds[].class));
        //then
        Assertions.assertFalse(actualList.isEmpty());
        BookDtoWithoutCategoryIds actualFirst = actualList.get(0);
        BookDtoWithoutCategoryIds actualSecond = actualList.get(1);
        EqualsBuilder.reflectionEquals(actualFirst, expectedFirst, "id");
        EqualsBuilder.reflectionEquals(actualSecond, expectedSecond, "id");
    }

    @Test
    @Sql(scripts = "classpath:database/books/add-categories-to-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-categories-from-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("""
            Get all categories
            """)
    public void getAll_ReturnAllCategories_Success() throws Exception {
        //given
        CategoryDto expectedFirst = new CategoryDto()
                .setName("UkrLit")
                .setDescription("Ukr");
        CategoryDto expectedSecond = new CategoryDto()
                .setName("UsaLit")
                .setDescription("USA");
        //when
        MvcResult result = mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andReturn();
        List<CategoryDto> actualList = Arrays.asList(objectMapper
                .readValue(result.getResponse().getContentAsString(), CategoryDto[].class));
        //then
        Assertions.assertFalse(actualList.isEmpty());
        CategoryDto actualFirst = actualList.get(0);
        CategoryDto actualSecond = actualList.get(1);
        EqualsBuilder.reflectionEquals(actualFirst, expectedFirst, "id");
        EqualsBuilder.reflectionEquals(actualSecond, expectedSecond, "id");
    }

    @Test
    @Sql(scripts = "classpath:database/books/add-categories-to-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-categories-from-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("""
            Get category by id
            """)
    public void getCategoryById_ReturnCorrectCategory_Success() throws Exception {
        //given
        Long categoryId = 1L;
        CategoryDto expected = new CategoryDto()
                .setName("UkrLit")
                .setDescription("Ukr");
        //when
        MvcResult result = mockMvc.perform(get("/categories/{id}", categoryId))
                .andExpect(status().isOk())
                .andReturn();
        CategoryDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), CategoryDto.class);
        //then
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(actual.getId(), categoryId);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @Sql(scripts = "classpath:database/books/delete-category-after-test-adding.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("""
            Add new category to db
            """)
    public void createCategory_AddCategoryToDb_Success() throws Exception {
        //given
        CategoryDto expected = new CategoryDto()
                .setId(1L)
                .setName("Poem")
                .setDescription("Borring");
        CreateRequestCategoryDto categoryDto = new CreateRequestCategoryDto(
                1L,
                "Poem",
                "Borring"
        );
        String jsonResult = objectMapper.writeValueAsString(categoryDto);
        //when
        MvcResult result = mockMvc.perform(post("/categories")
                        .content(jsonResult)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        CategoryDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                CategoryDto.class);
        //then
        EqualsBuilder.reflectionEquals(actual, expected, "id");
    }

    @Test
    @Sql(scripts = "classpath:database/books/add-categories-to-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-categories-from-db-after-update.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("""
            Update category by id
            """)
    public void updateCategory_ReturnUpdatedCategory_Success() throws Exception {
        //given
        Long categoryId = 1L;
        CreateRequestCategoryDto categoryDto = new CreateRequestCategoryDto(
                1L,
                "Since",
                "Interesting"
        );
        CategoryDto expected = new CategoryDto()
                .setId(1L)
                .setName("Since")
                .setDescription("Interesting");
        String jsonRequest = objectMapper.writeValueAsString(categoryDto);
        //when
        MvcResult result = mockMvc.perform(put("/categories/{is}", categoryId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        CategoryDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), CategoryDto.class);
        //then
        Assertions.assertEquals(actual.getId(), categoryId);
        EqualsBuilder.reflectionEquals(actual, expected, "id");
    }

    @Test
    @Sql(scripts = "classpath:database/books/add-category-to-db-for-delete-method-test.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("""
            Delete category by id
            """)
    public void deleteCategory_DeleteCategoryFromDb_Success() throws Exception {
        //given
        Long categoryId = 1L;
        //when
        mockMvc.perform(delete("/categories/{id}", categoryId))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
