package book.store.onlinebookstore.dto;

public record BookSearchParametersDto(String[] titles,
                                      String[] authors,
                                      String[] isbns,
                                      String[] prices) {
}
