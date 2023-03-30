package developer.book.backend.dataAccess.abstracts;

import developer.book.backend.entities.concretes.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT r FROM Book r WHERE r.isbn LIKE %:isbn%")
    List<Book> findByIsbn(String isbn);

}
