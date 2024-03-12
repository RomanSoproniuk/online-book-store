package book.store.onlinebookstore.service;

import book.store.onlinebookstore.dto.bookdto.BookDto;
import book.store.onlinebookstore.dto.bookdto.BookSearchParametersDto;
import book.store.onlinebookstore.dto.bookdto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto bookRequestDto);

    public List<BookDto> search(BookSearchParametersDto params);
}
