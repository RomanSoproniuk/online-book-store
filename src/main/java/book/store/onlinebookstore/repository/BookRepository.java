package book.store.onlinebookstore.repository;

import book.store.onlinebookstore.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    Book save(Book book);

    List<Book> findAll();

    Optional<Book> findById(Long id);

    @EntityGraph(attributePaths = "categories")
    List<Book> findAllByCategoriesId(Pageable pageable, Long categoryId);

}
