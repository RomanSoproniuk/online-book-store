package book.store.onlinebookstore.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import book.store.onlinebookstore.dto.BookDto;
import book.store.onlinebookstore.dto.CreateBookRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;


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
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    void createBook_ValidRequestDto_Success() throws Exception {
        //given
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto()
                .setTitle("Lisova Mavka")
                .setAuthor("Lesya Ukrainka")
                .setIsbn("43623uefhef723")
                .setPrice(BigDecimal.valueOf(50))
                .setDescription("Awesome")
                .setCoverImage("SuperImage");
        BookDto bookDto = new BookDto()
                .setId(1L)
                .setTitle("Lisova Mavka")
                .setAuthor("Lesya Ukrainka")
                .setIsbn("43623uefhef723")
                .setPrice(BigDecimal.valueOf(50))
                .setDescription("Awesome")
                .setCoverImage("SuperImage");
        String jsonRequest = objectMapper.writeValueAsString(createBookRequestDto);
        System.out.println(jsonRequest);

        //when
        MvcResult result = mockMvc.perform(post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        //then
    }
}
