package book.store.onlinebookstore.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BookDtoWithoutCategoryIds {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private BigDecimal price;
    private String description;
    private String coverImage;

    public BookDtoWithoutCategoryIds() {
    }

    public BookDtoWithoutCategoryIds(Long id, String author, String title, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }
}
