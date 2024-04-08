package book.store.onlinebookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import book.store.onlinebookstore.dto.CartItemRequestDto;
import book.store.onlinebookstore.dto.CartItemUpdateRequestDto;
import book.store.onlinebookstore.dto.ShoppingCartResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.Principal;
import java.util.Set;
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
public class ShoppingCartControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final Principal principal = new Principal() {
        @Override
        public String getName() {
            return "roman@gmail.com";
        }
    };

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
            Add new books to shopping cart
            """)
    @WithMockUser(username = "roman@gmail.com", roles = {"ADMIN"})
    @Sql(scripts = "classpath:database/books/add-shopping-cart-with-cart-item-for-roman.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-shopping-cart-with-cart-item-for-roman.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addBookToShoppingCart_AddNewBookToShoppingCart_Success() throws Exception {
        //given
        CartItemRequestDto cartItemRequestDto = new CartItemRequestDto(1L, 1L, 5);
        String jsonRequest = objectMapper.writeValueAsString(cartItemRequestDto);

        //when
        MvcResult result = mockMvc.perform(post("/cart")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("""
            Get shopping cart by user
            """)
    @WithMockUser(username = "roman@gmail.com", roles = {"ADMIN"})
    @Sql(scripts = "classpath:database/books/add-shopping-cart-with-cart-item-for-roman.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-shopping-cart-with-cart-item-for-roman.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getShoppingCartByUser_ReturnCorrectShoppingCart_Success() throws Exception {
        //given
        ShoppingCartResponseDto expect = new ShoppingCartResponseDto()
                .setId(2L).setUsersId(2L).setCartItems(Set.of());
        //when
        MvcResult result = mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andReturn();
        //then
        ShoppingCartResponseDto actual = objectMapper.readValue(result
                        .getResponse().getContentAsString(),
                ShoppingCartResponseDto.class);
        EqualsBuilder.reflectionEquals(expect, actual, "cartItems");
    }

    @Test
    @DisplayName("""
            Update book quantity in cart item by id
            """)
    @WithMockUser(username = "roman@gmail.com", roles = {"ADMIN"})
    @Sql(scripts = "classpath:database/books/add-shopping-cart-with-cart-item-for-roman.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-shopping-cart-with-cart-item-for-roman.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateBooksQuantityInCartItem_UpdateCartItemById_Success() throws Exception {
        //given
        Long cartItemId = 1L;
        CartItemUpdateRequestDto cartItemUpdateRequestDto = new CartItemUpdateRequestDto()
                .setQuantity(10);
        String jsonRequest = objectMapper.writeValueAsString(cartItemUpdateRequestDto);
        //when
        mockMvc.perform(put("/cart/cart-items/{cartItemId}", cartItemId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("""
            Delete book quantity in cart item by id
            """)
    @WithMockUser(username = "roman@gmail.com", roles = {"ADMIN"})
    @Sql(scripts = "classpath:database/books/add-shopping-cart-with-cart-item-for-roman.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-shopping-cart-with-cart-item-for-roman.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteCartItemFromShoppingCart_DeleteCartItemById_Success() throws Exception {
        //given
        Long cardItemId = 2L;
        //when
        mockMvc.perform(delete("/cart/cart-items/{cartItemId}", cardItemId))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
