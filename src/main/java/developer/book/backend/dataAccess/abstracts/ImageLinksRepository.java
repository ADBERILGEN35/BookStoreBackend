package developer.book.backend.dataAccess.abstracts;

import developer.book.backend.entities.concretes.ImageLinks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageLinksRepository extends JpaRepository<ImageLinks, Long> {
}
