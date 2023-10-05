package developer.book.backend.business.responses;

import developer.book.backend.entities.concretes.Book;
import developer.book.backend.entities.concretes.ImageLinks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
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
            this.title = book.getTitle();
            this.authors = book.getAuthors().stream()
                    .map(author -> new AuthorResponse(author.getName())).collect(Collectors.toList());
            this.isbn = book.getIsbn();
            this.publisher = book.getPublisher();
            this.publishedDate = book.getPublishedDate();
            this.pageCount = book.getPageCount();
            this.description = book.getDescription();
            this.imageLinks = book.getImageLinks();
        }
    }
}