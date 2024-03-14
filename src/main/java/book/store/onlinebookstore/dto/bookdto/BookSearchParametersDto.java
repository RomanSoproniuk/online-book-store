package book.store.onlinebookstore.dto.bookdto;

public record BookSearchParametersDto(String[] titles,
                                      String[] authors,
                                      String[] isbns,
                                      String[] prices) {
}
