package book.store.onlinebookstore.repository;

import book.store.onlinebookstore.model.Order;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"orderItems", "user"})
    List<Order> findAllByUserEmail(String userEmail, Pageable pageable);
}
