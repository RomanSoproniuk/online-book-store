package book.store.onlinebookstore.service;

import book.store.onlinebookstore.dto.BookDto;
import book.store.onlinebookstore.dto.BookDtoWithoutCategoryIds;
import book.store.onlinebookstore.dto.CreateBookRequestDto;
import book.store.onlinebookstore.mapper.BookMapper;
import book.store.onlinebookstore.model.Book;
import book.store.onlinebookstore.repository.BookRepository;
import book.store.onlinebookstore.service.impl.BookServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServiceImpl bookService;
    private final Pageable pageable = PageRequest.of(1, 2);
    private CreateBookRequestDto createBookRequestDto;
    private CreateBookRequestDto createSecondBookRequestDto;
    private Book book;
    private Book secondBook;
    private Book savedBook;
    private Book savedSecondBook;
    private BookDto expectedDto;
    private BookDto secondExpectedDto;
    private BookDtoWithoutCategoryIds expectedDtoWithOutCategory;
    private BookDtoWithoutCategoryIds expectedSecondDtoWithOutCategory;

    @BeforeEach
    public void setUp() {
        createBookRequestDto = new CreateBookRequestDto("Kobzar", "Shechenko",
                BigDecimal.valueOf(50L));
        createSecondBookRequestDto = new CreateBookRequestDto("Lisova Mavka", "Ukrainka",
                BigDecimal.valueOf(100L));
        book = new Book("Kobzar", "Shechenko", BigDecimal.valueOf(50L));
        secondBook = new Book("Lisova Mavka", "Ukrainka", BigDecimal.valueOf(100L));
        savedBook = new Book(1L, "Kobzar", "Shechenko", BigDecimal.valueOf(50L));
        savedSecondBook = new Book(2L, "Lisova Mavka", "Ukrainka", BigDecimal.valueOf(100L));
        expectedDto = new BookDto(1L, "Kobzar", "Shechenko", BigDecimal.valueOf(50L));
        secondExpectedDto = new BookDto(2L, "Lisova Mavka", "Ukrainka", BigDecimal.valueOf(100L));
        expectedDtoWithOutCategory = new BookDtoWithoutCategoryIds(1L, "Shechenko",
                "Kobzar",BigDecimal.valueOf(50L));
        expectedSecondDtoWithOutCategory = new BookDtoWithoutCategoryIds(2L, "Ukrainka",
                "Lisova Mavka", BigDecimal.valueOf(100L));
    }

    @Test
    @DisplayName("""
            Check if saving process is correct and after return correct entity
            """)
    public void saveBook_SavedBook_CheckCorrectReturnBookDto() {
        //given
        Mockito.when(bookMapper.toEntity(createBookRequestDto)).thenReturn(book);
        Mockito.when(bookRepository.save(book)).thenReturn(savedBook);
        Mockito.when(bookMapper.toDto(savedBook)).thenReturn(expectedDto);
        //when
        BookDto actualDto = bookService.save(createBookRequestDto);
        //then
        Assertions.assertEquals(expectedDto.getId(), actualDto.getId());
        Assertions.assertEquals(expectedDto.getTitle(), actualDto.getTitle());
        Assertions.assertEquals(expectedDto.getAuthor(), actualDto.getAuthor());
        Assertions.assertEquals(expectedDto.getPrice(), actualDto.getPrice());
    }

    @Test
    @DisplayName("""
            Check if get all process is correct and after return correct entity
            """)
    public void findAll_ReturnAllBooks_ReturnSameBooks() {
        //given
        Page<Book> categoryPage = new PageImpl<>(List.of(savedBook, savedSecondBook));
        Mockito.when(bookRepository.findAll(pageable)).thenReturn(categoryPage);
        Mockito.when(bookMapper.toDto(savedBook)).thenReturn(expectedDto);
        Mockito.when(bookMapper.toDto(savedSecondBook)).thenReturn(secondExpectedDto);
        List<BookDto> expectedList = List.of(expectedDto, secondExpectedDto);
        int expectedListSize = expectedList.size();
        //when
        List<BookDto> actualList = bookService.findAll(pageable);
        int actualListSize = actualList.size();
        BookDto actualDto = actualList.get(0);
        BookDto actualSecondDto = actualList.get(1);
        //then
        Assertions.assertEquals(expectedListSize, actualListSize);
        Assertions.assertEquals(expectedDto.getTitle(),
                actualDto.getTitle());
        Assertions.assertEquals(expectedDto.getAuthor(),
                actualDto.getAuthor());
        Assertions.assertEquals(expectedDto.getId(),
                actualDto.getId());
        Assertions.assertEquals(actualSecondDto.getId(),
                secondExpectedDto.getId());
    }

    @Test
    @DisplayName("""
            Verify if return correct book by id
            """)
    public void findById_ReturnBookById_ReturnCorrectBookDto() {
        //given
        BookDto expectedBookDto = expectedDto;
        Long bookId = 1L;
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(savedBook));
        Mockito.when(bookMapper.toDto(savedBook)).thenReturn(expectedBookDto);
        //when
        BookDto actualBookDto = bookService.findById(bookId);
        //then
        Assertions.assertEquals(expectedDto.getTitle(), actualBookDto.getTitle());
        Assertions.assertEquals(expectedDto.getAuthor(), actualBookDto.getAuthor());
        Assertions.assertEquals(expectedDto.getId(), actualBookDto.getId());
    }

    @Test
    @DisplayName("""
            Verify if return correct book by id after updating book
            """)
    public void update_UpdateBookById_ReturnCorrectBookDto() {
        //given
        Long bookId = 2L;
        Mockito.when(bookMapper.toEntity(createSecondBookRequestDto)).thenReturn(secondBook);
        Mockito.when(bookRepository.save(secondBook)).thenReturn(savedBook);
        Mockito.when(bookMapper.toDto(savedBook)).thenReturn(secondExpectedDto);
        //when
        BookDto actualBookRequestDto = bookService.update(bookId, createSecondBookRequestDto);
        //then
        Assertions.assertEquals(secondExpectedDto.getId(), actualBookRequestDto.getId());
        Assertions.assertEquals(secondExpectedDto.getTitle(), actualBookRequestDto.getTitle());
        Assertions.assertEquals(secondExpectedDto.getAuthor(), actualBookRequestDto.getAuthor());
    }

    @Test
    @DisplayName("""
            Verify if return correct book by categories id
            """)
    public void getAllByCategoriesId_GetAllBookByCategoryId_ReturnCorrectBook() {
        //given
        Long categoryId = 1L;
        Mockito.when(bookRepository.findAllByCategoriesId(pageable, categoryId))
                .thenReturn(List.of(savedBook, savedSecondBook));
        Mockito.when(bookMapper.toDtoWithoutCategoryIds(savedBook))
                .thenReturn(expectedDtoWithOutCategory);
        Mockito.when(bookMapper.toDtoWithoutCategoryIds(savedSecondBook))
                .thenReturn(expectedSecondDtoWithOutCategory);
        List<BookDtoWithoutCategoryIds> expectedList
                = List.of(expectedDtoWithOutCategory, expectedSecondDtoWithOutCategory);
        int expectedListSize = expectedList.size();
        //when
        List<BookDtoWithoutCategoryIds> actualList
                = bookService.getAllByCategoriesId(pageable, categoryId);
        int actualListSize = actualList.size();
        //then
        Assertions.assertEquals(expectedListSize, actualListSize);
        Assertions.assertEquals(expectedList.get(0).getTitle(), actualList.get(0).getTitle());
        Assertions.assertEquals(expectedList.get(1).getAuthor(), actualList.get(1).getAuthor());
        Assertions.assertEquals(expectedList.get(1).getPrice(), actualList.get(1).getPrice());
    }
}
