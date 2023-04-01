package developer.book.backend.dataAccess.abstracts;

import developer.book.backend.entities.concretes.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);

    @Query("SELECT r FROM Book r WHERE r.isbn LIKE %:isbn%")
    List<Book> findByIsbn(String isbn);

    @Modifying
    @Query("DELETE FROM Book b WHERE b.title IS NULL OR b.authors IS EMPTY OR b.isbn IS NULL OR b.publisher IS NULL OR b.publishedDate IS NULL OR b.pageCount IS NULL OR b.description IS NULL OR b.imageLinks IS NULL")
    void deleteBooksIfAnyFieldIsNull();

}
