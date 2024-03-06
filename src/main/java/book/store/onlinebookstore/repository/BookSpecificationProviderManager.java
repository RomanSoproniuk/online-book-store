package book.store.onlinebookstore.repository;

import book.store.onlinebookstore.exceptions.EntityNotFoundException;
import book.store.onlinebookstore.model.Book;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviderList;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviderList.stream()
                .filter(provider -> provider.getKey().equals(key))
                .findFirst().orElseThrow(() ->
                        new EntityNotFoundException("Can't find specification "
                        + "provider by key " + key));
    }
}
