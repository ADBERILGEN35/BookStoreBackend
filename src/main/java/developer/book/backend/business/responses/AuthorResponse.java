package developer.book.backend.business.responses;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponse {
    private Long id;
    private String name;

}
