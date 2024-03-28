package book.store.onlinebookstore.service;

import book.store.onlinebookstore.dto.CategoryDto;
import book.store.onlinebookstore.dto.CreateRequestCategoryDto;
import book.store.onlinebookstore.exceptions.EntityNotFoundException;
import book.store.onlinebookstore.mapper.impl.CategoryMapperImpl;
import book.store.onlinebookstore.model.Category;
import book.store.onlinebookstore.repository.CategoryRepository;
import book.store.onlinebookstore.service.impl.CategoryServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapperImpl categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;
    private final Pageable pageable = PageRequest.of(1, 2);
    private Category firstCategory;
    private Category secondCategory;
    private CategoryDto firstCategoryDto;
    private CategoryDto secondCategoryDto;


    @BeforeEach
    public void setUp() {
        firstCategory = new Category();
        firstCategory.setId(1L);
        firstCategory.setName("Criminal");
        firstCategory.setDescription("Awesome and scared");
        secondCategory = new Category();
        secondCategory.setId(2L);
        secondCategory.setName("Fantastic");
        secondCategory.setDescription("About miracle worlds");
        firstCategoryDto = new CategoryDto();
        firstCategoryDto.setId(1L);
        firstCategoryDto.setName("Criminal");
        firstCategoryDto.setDescription("Awesome and scared");
        secondCategoryDto = new CategoryDto();
        secondCategoryDto.setId(2L);
        secondCategoryDto.setName("Fantastic");
        secondCategoryDto.setDescription("About miracle worlds");
    }

    @Test
    @DisplayName("""
            Verify if all categories returned
            """)
    public void findAll_ReturnAllCategory_ShouldReturnAllCategories() {
        //given
        Page<Category> categoryPage = new PageImpl<>(List.of(firstCategory, secondCategory), pageable, 5);
        Mockito.when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        Mockito.when(categoryMapper.toDto(firstCategory)).thenReturn(firstCategoryDto);
        Mockito.when(categoryMapper.toDto(secondCategory)).thenReturn(secondCategoryDto);
        List<CategoryDto> expectedList = List.of(firstCategoryDto, secondCategoryDto);
        int expectedListSize = expectedList.size();
        CategoryDto expectedFirstCategoryDto = expectedList.get(0);
        CategoryDto expectedSecondCategoryDto = expectedList.get(1);
        //when
        List<CategoryDto> actualList = categoryService.findAll(pageable);
        int actualListSize = actualList.size();
        CategoryDto actualFirstCategoryDto = actualList.get(0);
        CategoryDto actualSecondCategoryDto = actualList.get(1);
        //then
        Assertions.assertEquals(expectedListSize, actualListSize);
        Assertions.assertEquals(expectedFirstCategoryDto.getName(),
                actualFirstCategoryDto.getName());
        Assertions.assertEquals(expectedFirstCategoryDto.getDescription(),
                actualFirstCategoryDto.getDescription());
        Assertions.assertEquals(expectedSecondCategoryDto.getName(),
                actualSecondCategoryDto.getName());
        Assertions.assertEquals(expectedSecondCategoryDto.getDescription(),
                actualSecondCategoryDto.getDescription());
    }

    @Test
    @DisplayName("""
            Verify if return correct category by id
            """)
    public void getById_ReturnCategoryById_ReturnCorrectCategory() {
        //given
        CategoryDto actualCategory = firstCategoryDto;
        Long categoryId = 1L;
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(firstCategory));
        Mockito.when(categoryMapper.toDto(firstCategory)).thenReturn(firstCategoryDto);
        //when
        CategoryDto expectedCategory = categoryService.getById(categoryId);
        //then
        Assertions.assertEquals(actualCategory.getName(), expectedCategory.getName());
        Assertions.assertEquals(actualCategory.getDescription(), expectedCategory.getDescription());
        Assertions.assertEquals(actualCategory.getId(), expectedCategory.getId());
    }

    @Test
    @DisplayName("""
            Verify if return correct category by non existing id
            """)
    public void getById_WithNonExistingCategoryId_ShouldReturnException() {
        //given
        Long categoryId = 100L;
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        //when
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> categoryService.getById(categoryId));
        //then
        String expected = "Can't find category by id: " + categoryId;
        String actual = exception.getMessage();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Verify if return correct category after saving
            """)
    public void save_ReturnCorrectCategoryAfterSaving_ShouldCategoryWithSameValue() {
        //given
        CreateRequestCategoryDto categoryDto = new CreateRequestCategoryDto(
                1L,
                "Fantastic",
                "Awesome");
        Category category = new Category();
        category.setId(1L);
        category.setName("Fantastic");
        category.setDescription("Awesome");
        CategoryDto categoryDtoResponse = new CategoryDto();
        categoryDtoResponse.setId(1L);
        categoryDtoResponse.setName("Fantastic");
        categoryDtoResponse.setDescription("Awesome");
        Mockito.when(categoryMapper.toEntity(categoryDto)).thenReturn(category);
        Mockito.when(categoryRepository.save(category)).thenReturn(category);
        Mockito.when(categoryMapper.toDto(category)).thenReturn(categoryDtoResponse);
        Long expectId = 1L;
        String expectName = "Fantastic";
        String expectDescription = "Awesome";
        //when
        CategoryDto actualCategoryDto = categoryService.save(categoryDto);
        Long actualId = actualCategoryDto.getId();
        String actualName = actualCategoryDto.getName();
        String actualDescription = actualCategoryDto.getDescription();
        //then
        Assertions.assertEquals(expectId, actualId);
        Assertions.assertEquals(expectName, actualName);
        Assertions.assertEquals(expectDescription, actualDescription);
    }

    @Test
    @DisplayName("""
            Verify if return correct category after update category by id
            """)
    public void update_ReturnUpdatedCategory_ShouldReturnCorrectCategory() {
        //given
        Long categoryId = 1L;
        CreateRequestCategoryDto categoryDto
                = new CreateRequestCategoryDto(
                1L,
                "Non Fiction",
                "Interesting"
        );
        CategoryDto expectedDto = new CategoryDto();
        firstCategory.setId(categoryId);
        firstCategory.setName("Non Fiction");
        firstCategory.setDescription("Interesting");
        expectedDto.setId(categoryId);
        expectedDto.setName("Non Fiction");
        expectedDto.setDescription("Interesting");
        Mockito.when(categoryMapper.toEntity(categoryDto)).thenReturn(firstCategory);
        Mockito.when(categoryRepository.save(firstCategory)).thenReturn(firstCategory);
        Mockito.when(categoryMapper.toDto(firstCategory)).thenReturn(expectedDto);
        //when
        CategoryDto actualDto = categoryService.update(categoryId, categoryDto);
        //then
        Assertions.assertEquals(expectedDto.getId(), actualDto.getId());
        Assertions.assertEquals(expectedDto.getName(), actualDto.getName());
        Assertions.assertEquals(expectedDto.getDescription(), actualDto.getDescription());
    }

}
