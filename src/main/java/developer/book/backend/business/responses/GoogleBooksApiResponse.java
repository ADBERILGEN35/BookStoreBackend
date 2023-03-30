package developer.book.backend.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleBooksApiResponse {
    private List<Item> items;
    private Integer totalItems;

    @Data
    public static class Item {
        private VolumeInfo volumeInfo;
    }

    @Data
    public static class VolumeInfo {
        private String title;
        private List<String> authors;
        private List<IndustryIdentifier> industryIdentifiers;
        private String publisher;
        private String publishedDate;
        private Integer pageCount;
        private String description;
        private ImageLinks imageLinks;
    }

    @Data
    public static class IndustryIdentifier {
        private String type;
        private String identifier;
    }

    @Data
    public static class ImageLinks {
        private String smallThumbnail;
        private String thumbnail;
    }
}
