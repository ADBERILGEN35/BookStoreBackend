package developer.book.backend.business.responses;

import developer.book.backend.entities.concretes.Author;
import developer.book.backend.entities.concretes.Book;
import developer.book.backend.entities.concretes.ImageLinks;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
    private List<AuthorResponse> authors;
    private String isbn;
    private String publisher;
    private String publishedDate;
    private Integer pageCount;
    private String description;
    private ImageLinks imageLinks;

    public BookResponse(Book book) {
        if (book != null) {
            this.id = book.getId();
            this.title = book.getTitle();
            this.authors = book.getAuthors().stream()
                    .map(author -> new AuthorResponse(author.getId(), author.getName())).collect(Collectors.toList());
            this.isbn = book.getIsbn();
            this.publisher = book.getPublisher();
            this.publishedDate = book.getPublishedDate();
            this.pageCount = book.getPageCount();
            this.description = book.getDescription();
            this.imageLinks = book.getImageLinks();
        }
    }
}