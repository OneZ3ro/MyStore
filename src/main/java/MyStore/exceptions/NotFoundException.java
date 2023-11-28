package MyStore.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String elemType, UUID elemId) {
        super(elemType + " with ID: '" + elemId + " ' was not found. Please try again with another ID");
    }

    public NotFoundException(String elemType, long elemId) {
        super(elemType + " with ID: '" + elemId + " ' was not found. Please try again with another ID");
    }

    public NotFoundException(String email) {
        super(email + " ' was not found. Please try again with another email");
    }
}
