package developer.book.backend.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageLinksResponse {
    private String smallThumbnail;
    private String thumbnail;
}
