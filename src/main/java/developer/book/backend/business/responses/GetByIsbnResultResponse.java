package developer.book.backend.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetByIsbnResultResponse {
    private Long id;
    private String title;
    private List<AuthorResponse> authors;
    private String isbn;
    private String publisher;
    private String publishedDate;
    private Integer pageCount;
    private String description;
    private GoogleBooksApiResponse.ImageLinks thumbnailUrl;

}
