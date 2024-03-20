package book.store.onlinebookstore.repository;

import book.store.onlinebookstore.model.OrderItem;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @EntityGraph(attributePaths = {"order", "book"})
    List<OrderItem> findAllByOrderId(Long orderId);

    @EntityGraph(attributePaths = {"order", "book"})
    List<OrderItem> findAllByOrderId(Long orderId, Pageable pageable);

    @EntityGraph(attributePaths = {"order", "book"})
    List<OrderItem> findAllByOrderUserId(Long userId, Pageable pageable);
}
