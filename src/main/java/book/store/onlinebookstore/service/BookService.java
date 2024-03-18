package book.store.onlinebookstore.service;

import book.store.onlinebookstore.dto.bookdto.BookDto;
import book.store.onlinebookstore.dto.bookdto.BookDtoWithoutCategoryIds;
import book.store.onlinebookstore.dto.bookdto.BookSearchParametersDto;
import book.store.onlinebookstore.dto.bookdto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto bookRequestDto);

    List<BookDto> search(BookSearchParametersDto params);

    List<BookDtoWithoutCategoryIds> getAllByCategoriesId(Pageable pageable, Long categoryId);
}
