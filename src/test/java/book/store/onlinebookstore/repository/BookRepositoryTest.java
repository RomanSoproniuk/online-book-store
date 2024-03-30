package book.store.onlinebookstore.repository;

import book.store.onlinebookstore.model.Book;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    private final Pageable pageable = PageRequest.of(0, 5);

    @Test
    @DisplayName("""
            Test repository method findAllByCategoriesId, should return correct book categories
            """)
    @Sql(scripts = "classpath:database/books/add-book-and-category-to-repository.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-book-and-category-from-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByCategoriesId_ReturnAllBookByCategoryId_ShouldReturnOneBook() {
        //given
        Long categoryId = 1L;
        Long expectedBookId = 1L;
        String expectedBookTitle = "Kobzar";
        String expectedBookAuthor = "Shevchenko";
        BigDecimal expectedPrice = BigDecimal.valueOf(50);
        String expectedIsbn = "3ih3348wjew";
        //when
        List<Book> allByCategoriesId = bookRepository.findAllByCategoriesId(pageable, categoryId);
        Long actualId = allByCategoriesId.get(0).getId();
        String actualTitle = allByCategoriesId.get(0).getTitle();
        BigDecimal actualPrice = allByCategoriesId.get(0).getPrice();
        String actualAuthor = allByCategoriesId.get(0).getAuthor();
        String actualIsbn = allByCategoriesId.get(0).getIsbn();
        //then
        Assertions.assertEquals(expectedBookId, actualId);
        Assertions.assertEquals(expectedBookTitle, actualTitle);
        Assertions.assertEquals(expectedBookAuthor, actualAuthor);
        Assertions.assertEquals(expectedPrice, actualPrice);
        Assertions.assertEquals(expectedIsbn, actualIsbn);
    }
}
