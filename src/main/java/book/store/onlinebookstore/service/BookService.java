package book.store.onlinebookstore.service;

import book.store.onlinebookstore.dto.BookDto;
import book.store.onlinebookstore.dto.BookSearchParametersDto;
import book.store.onlinebookstore.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto bookRequestDto);

    public List<BookDto> search(BookSearchParametersDto params);
}
