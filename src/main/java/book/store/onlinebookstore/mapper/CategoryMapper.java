package book.store.onlinebookstore.mapper;

import book.store.onlinebookstore.config.MapperConfig;
import book.store.onlinebookstore.dto.categorydto.CategoryDto;
import book.store.onlinebookstore.dto.categorydto.CreateRequestCategoryDto;
import book.store.onlinebookstore.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toEntity(CreateRequestCategoryDto categoryDto);
}
