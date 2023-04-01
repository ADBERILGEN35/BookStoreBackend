package developer.book.backend.business.rules;

import developer.book.backend.core.utilies.exceptions.BusinnessException;
import developer.book.backend.dataAccess.abstracts.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BookBusinessRules {
    private BookRepository bookRepository;

    public void checkIfBookIsbnExists(String isbn) {
        if (this.bookRepository.existsByIsbn(isbn)) {
            throw new BusinnessException("Book ISBN already exists");
        }
    }

}
