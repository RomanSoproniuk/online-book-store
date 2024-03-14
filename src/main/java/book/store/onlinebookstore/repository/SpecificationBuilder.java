package book.store.onlinebookstore.repository;

import book.store.onlinebookstore.dto.bookdto.BookSearchParametersDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParametersDto bookSearchParametersDto);
}
