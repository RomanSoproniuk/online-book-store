package book.store.onlinebookstore;

import book.store.onlinebookstore.model.Book;
import book.store.onlinebookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book lisovaPisnia = new Book();
                lisovaPisnia.setTitle("Lisova Pisnia");
                lisovaPisnia.setAuthor("Lesia Ukrainka");
                lisovaPisnia.setPrice(BigDecimal.valueOf(450));
                lisovaPisnia.setIsbn("qwerty1");
                lisovaPisnia.setDescription("Awesome book about forest and his inhabitant");
                Book savedLisovaPisnia = bookService.save(lisovaPisnia);
                System.out.println(savedLisovaPisnia);
            }
        };
    }

}
