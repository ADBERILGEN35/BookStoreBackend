package developer.book.backend.dataAccess.abstracts;

import developer.book.backend.entities.concretes.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNameIgnoreCase(String name);

}
