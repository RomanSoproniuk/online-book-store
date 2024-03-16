package book.store.onlinebookstore.dto.bookdto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    private Long id;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 255)
    private String title;
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 255)
    private String author;
    @NotNull
    @NotEmpty
    private String isbn;
    @NotNull
    @Min(0)
    private BigDecimal price;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    @NotEmpty
    private String coverImage;
}
