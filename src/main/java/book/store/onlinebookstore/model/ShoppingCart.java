package book.store.onlinebookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity(name = "shopping_carts")
@Getter
@Setter
@SQLDelete(sql = "UPDATE shopping_carts SET is_deleted = true WHERE id=?")
@SQLRestriction(value = "is_deleted = FALSE")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @PrimaryKeyJoinColumn
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToMany(mappedBy = "shoppingCart", fetch = FetchType.LAZY)
    private Set<CartItem> cartItems = new HashSet<>();
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
