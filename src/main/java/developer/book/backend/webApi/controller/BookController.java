package developer.book.backend.webApi.controller;

import developer.book.backend.business.abstracts.BookService;
import developer.book.backend.business.responses.BookResponse;
import developer.book.backend.business.responses.GetByIdResultResponse;
import developer.book.backend.business.responses.GetByIsbnResultResponse;
import developer.book.backend.entities.concretes.Book;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/save-all")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> saveAllBooks() {
        bookService.saveAllBooksFromGoogleBooksApi();
        return ResponseEntity.ok("All books saved");
    }

    @GetMapping()
    public List<BookResponse> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/isbn/{isbn}")
    public List<GetByIsbnResultResponse> getByIsbnResultResponse(@PathVariable String isbn) {
        return bookService.getByIsbn(isbn);
    }

    @GetMapping("/{id}")
    public GetByIdResultResponse getByIdResultResponse(@PathVariable long id) {
        return bookService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        this.bookService.delete(id);
    }

    @DeleteMapping("/null-fields")
    public ResponseEntity<String> deleteBooksIfAnyFieldIsNull() {
        bookService.deleteBooksIfAnyFieldIsNull();
        return ResponseEntity.ok("Null fields have been removed from the books.");
    }

}
