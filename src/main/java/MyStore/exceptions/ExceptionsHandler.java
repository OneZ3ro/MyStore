package MyStore.exceptions;

import MyStore.payloads.exceptions.ExceptionsDTO;
import MyStore.payloads.exceptions.ExceptionsWithListDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionsWithListDTO handleBadRequest(BadRequestException exception) {
        if (exception.getErrorList() != null) {
            List<String> errorList = exception.getErrorList().stream().map(objectError -> objectError.getDefaultMessage()).toList();
            return new ExceptionsWithListDTO(exception.getMessage(), new Date(), errorList);
        } else {
            return new ExceptionsWithListDTO(exception.getMessage(), new Date(), new ArrayList<>());
        }
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    public ExceptionsDTO handleUnauthorized(UnauthorizedException exception) {
        return new ExceptionsDTO(exception.getMessage(), new Date());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    public ExceptionsDTO handleAccessDenied(AccessDeniedException exception) {
        return new ExceptionsDTO(exception.getMessage(), new Date());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    public ExceptionsDTO handleNotFound(NotFoundException exception) {
        return new ExceptionsDTO(exception.getMessage(), new Date());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    public ExceptionsDTO handleGeneric(Exception exception) {
        exception.printStackTrace();
        return new ExceptionsDTO("Problema lato server...giuro che lo risolveremo presto", new Date());
    }
}
