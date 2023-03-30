package developer.book.backend.business.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private String title;
    private String isbn;
    private String publisher;
    private LocalDate publishedDate;
    private Integer pageCount;
    private String description;
    private String thumbnailUrl;
    private List<String> authorNames;

}
