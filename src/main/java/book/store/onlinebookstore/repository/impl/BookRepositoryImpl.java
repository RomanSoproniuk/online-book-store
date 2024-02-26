package book.store.onlinebookstore.repository.impl;

import book.store.onlinebookstore.exceptions.DataProcessingException;
import book.store.onlinebookstore.model.Book;
import book.store.onlinebookstore.repository.BookRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    public BookRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Book save(Book book) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save book :"
                    + book + ", to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Book> bookQuery = session.createQuery("SELECT * FROM Book b", Book.class);
            return bookQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all books from DB", e);
        }
    }
}
