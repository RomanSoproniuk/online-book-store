package book.store.onlinebookstore.repository;

import book.store.onlinebookstore.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM users u LEFT JOIN FETCH u.roles WHERE u.email=:email")
    Optional<User> findByEmail(@Param("email") String email);
}
