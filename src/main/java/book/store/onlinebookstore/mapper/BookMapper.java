package book.store.onlinebookstore.mapper;

import book.store.onlinebookstore.config.MapperConfig;
import book.store.onlinebookstore.dto.BookDto;
import book.store.onlinebookstore.dto.BookDtoWithoutCategoryIds;
import book.store.onlinebookstore.dto.CreateBookRequestDto;
import book.store.onlinebookstore.model.Book;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toEntity(CreateBookRequestDto bookRequestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategoryIds(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        bookDto.setCategories(book.getCategories());
    }

    @Named("getBookId")
    default Long getBookId(Book book) {
        return book.getId();
    }

    @Named("getBookName")
    default String getBookName(Book book) {
        return book.getTitle();
    }
}
