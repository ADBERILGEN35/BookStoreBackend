package developer.book.backend;

import developer.book.backend.core.utilies.exceptions.BusinnessException;
import developer.book.backend.core.utilies.exceptions.ProblemDetails;
import developer.book.backend.core.utilies.exceptions.ValidationProblemDetails;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@SpringBootApplication
public class BookBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookBackendApplication.class, args);
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ProblemDetails handleBusinessException(BusinnessException businnessException) {
        ProblemDetails problemDetails = new ProblemDetails();
        problemDetails.setMessage(businnessException.getMessage());

        return problemDetails;
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ProblemDetails handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        ValidationProblemDetails validationProblemDetails = new ValidationProblemDetails();
        validationProblemDetails.setMessage("VALIDATION.EXCEPTION");
        validationProblemDetails.setValidatinErrors(new HashMap<String, String>());

        for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
            validationProblemDetails.getValidatinErrors().put(fieldError.getField(), fieldError.getDefaultMessage());
        }


        return validationProblemDetails;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
