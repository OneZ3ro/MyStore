package MyStore.payloads.exceptions;

import java.util.Date;
import java.util.List;

public record ExceptionsWithListDTO(String message, Date timestamp, List<String> errorList) {
}
