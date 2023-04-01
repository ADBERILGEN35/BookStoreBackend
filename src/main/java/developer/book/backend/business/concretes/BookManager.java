package developer.book.backend.business.concretes;

import developer.book.backend.business.abstracts.BookService;
import developer.book.backend.business.responses.BookResponse;
import developer.book.backend.business.responses.GetByIdResultResponse;
import developer.book.backend.business.responses.GetByIsbnResultResponse;
import developer.book.backend.business.responses.GoogleBooksApiResponse;
import developer.book.backend.business.rules.BookBusinessRules;
import developer.book.backend.core.utilies.mappers.ModelMapperService;
import developer.book.backend.dataAccess.abstracts.AuthorRepository;
import developer.book.backend.dataAccess.abstracts.BookRepository;
import developer.book.backend.dataAccess.abstracts.ImageLinksRepository;
import developer.book.backend.entities.concretes.Author;
import developer.book.backend.entities.concretes.Book;
import developer.book.backend.entities.concretes.ImageLinks;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookManager implements BookService {
    private final BookRepository bookRepository;
    private final ImageLinksRepository imageLinksRepository;
    private final AuthorRepository authorRepository;
    private final RestTemplate restTemplate;
    private BookBusinessRules bookBusinessRules;
    private ModelMapperService modelMapperService;

    private static final Logger LOG = LoggerFactory.getLogger(BookManager.class);

    @Scheduled(cron = "0 0 0 1 * *")
    public void saveAllBooksFromGoogleBooksApi() {
        int startIndex = 0;
        int maxResults = 40;
        int totalItems = Integer.MAX_VALUE;


        String baseUrl = "https://www.googleapis.com/books/v1/volumes?q=abasiyanik";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        while (startIndex < totalItems) {
            String url = String.format("%s&startIndex=%d&maxResults=%d", baseUrl, startIndex, maxResults);
            ResponseEntity<GoogleBooksApiResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, GoogleBooksApiResponse.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                LOG.warn("Failed to retrieve books from Google Books API");
                break;
            }

            GoogleBooksApiResponse apiResponse = response.getBody();

            assert apiResponse != null;
            if (apiResponse.getItems() == null) {
                LOG.info("Items are null, stopping loop...");
                break;
            }

            List<Book> books = new ArrayList<>();

            for (GoogleBooksApiResponse.Item item : apiResponse.getItems()) {
                GoogleBooksApiResponse.VolumeInfo volumeInfo = item.getVolumeInfo();
                if (volumeInfo == null) {
                    LOG.info("VolumeInfo is null, skipping book...");
                    continue;
                }

                Book book = mapBook(volumeInfo);
                books.add(book);
            }

            books.forEach(bkk -> this.bookBusinessRules.checkIfBookIsbnExists(bkk.getIsbn()));

            if (!books.isEmpty()) {
                bookRepository.saveAll(books);
            }

            startIndex += maxResults;
            totalItems = apiResponse.getTotalItems();
        }
    }

    private Book mapBook(GoogleBooksApiResponse.VolumeInfo volumeInfo) {
        BookResponse bookDto = modelMapperService.forResponse().map(volumeInfo, BookResponse.class);
        Book book = modelMapperService.forResponse().map(bookDto, Book.class);

        List<String> authors = volumeInfo.getAuthors();
        if (authors != null && !authors.isEmpty()) {
            List<Author> authorList = new ArrayList<>();
            for (String authorName : authors) {
                Author author = authorRepository.findByNameIgnoreCase(authorName)
                        .orElseGet(() -> authorRepository.save(new Author(authorName)));
                authorList.add(author);
            }
            book.setAuthors(authorList);
        }


        if (Objects.nonNull(book.getImageLinks())) {
            imageLinksRepository.save(Objects.requireNonNull(book.getImageLinks()));
        }

        List<GoogleBooksApiResponse.IndustryIdentifier> industryIdentifiers = volumeInfo.getIndustryIdentifiers();
        if (industryIdentifiers != null && !industryIdentifiers.isEmpty()) {
            String isbn = industryIdentifiers.stream()
                    .filter(identifier -> "ISBN_13".equals(identifier.getType()))
                    .map(GoogleBooksApiResponse.IndustryIdentifier::getIdentifier)
                    .findFirst()
                    .orElse(null);
            book.setIsbn(isbn);
        }

        return book;
    }


    @Override
    public List<BookResponse> getAll() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream()
                .map(bk -> this.modelMapperService.forResponse()
                        .map(bk, BookResponse.class)).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<GetByIsbnResultResponse> getByIsbn(String isbn) {
        List<Book> books = bookRepository.findByIsbn(isbn);
        return books.stream()
                .map(bk -> this.modelMapperService.forResponse()
                        .map(bk, GetByIsbnResultResponse.class)).collect(Collectors.toList());
    }

    @Override
    public GetByIdResultResponse getById(long id) {
        Book book = this.bookRepository.findById(id).orElseThrow();
        return this.modelMapperService.forResponse()
                .map(book, GetByIdResultResponse.class);
    }

    @Transactional
    public void deleteBooksIfAnyFieldIsNull() {
        bookRepository.deleteBooksIfAnyFieldIsNull();

    }
}