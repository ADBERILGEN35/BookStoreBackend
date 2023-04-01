package developer.book.backend.business.abstracts;

import developer.book.backend.business.responses.BookResponse;
import developer.book.backend.business.responses.GetByIdResultResponse;
import developer.book.backend.business.responses.GetByIsbnResultResponse;

import java.util.List;

public interface BookService {
    void saveAllBooksFromGoogleBooksApi();

    List<BookResponse> getAll();

    void delete(long id);

    List<GetByIsbnResultResponse> getByIsbn(String isbn);

    GetByIdResultResponse getById(long id);

    void deleteBooksIfAnyFieldIsNull();


}
