package pl.adamczerwinski097.example.i18n.core.configuration.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
    private String message;
    private int status;
    private Collection<String> errors;
    private final LocalDateTime timestamp = LocalDateTime.now();


    public ExceptionResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
